package co.com.jdti.springmvcsecurity.configurations;

import co.com.jdti.springmvcsecurity.services.IAccountService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private IAccountService accountService;

	@SneakyThrows
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		return httpSecurity
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/home/**",
					"/about/**",
					"/account/access-denied",
					"/account/login",
					"/account/register",
					"/webjars/**").permitAll()
				.requestMatchers("/superadmin/**").hasRole("SUPER_ADMIN")
				.requestMatchers("/admin/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
				.requestMatchers("/employee/**").hasAnyRole("SUPER_ADMIN", "ADMIN", "EMPLOYEE")
				.anyRequest().authenticated())
			.formLogin(formLogin -> formLogin
				.loginPage("/account/login")
				.loginProcessingUrl("/account/process-login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/account/welcome")
				.failureUrl("/account/login?error")
				.permitAll())
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.invalidSessionUrl("/account/login")
				.maximumSessions(1))
			.logout(logout -> logout
				.logoutUrl("/account/logout")
				.logoutSuccessUrl("/account/login")
				.permitAll())
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.accessDeniedPage("/account/access-denied"))
			.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

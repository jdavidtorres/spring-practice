package co.com.jdti.springmvcsecurity.configurations;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@SneakyThrows
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		return httpSecurity
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/home/**", "/about/**", "/account/**", "/webjars/**").permitAll()
				.requestMatchers("/superadmin/**").hasRole("SUPER_ADMIN")
				.requestMatchers("/admin/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
				.requestMatchers("/employee/**").hasAnyRole("SUPER_ADMIN", "ADMIN", "EMPLOYEE")
			)
			.formLogin(formLogin -> formLogin
				.loginPage("/account/login")
				.loginProcessingUrl("/account/process-login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/account/welcome")
				.failureUrl("/account/login?error")
				.permitAll()
			)
			.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

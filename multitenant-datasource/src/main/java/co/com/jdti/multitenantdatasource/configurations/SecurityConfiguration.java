package co.com.jdti.multitenantdatasource.configurations;

import co.com.jdti.multitenantdatasource.utils.MultitenantUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class SecurityConfiguration {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic(Customizer.withDefaults())
			.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
			.csrf(AbstractHttpConfigurer::disable);
		return httpSecurity.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		var rob = createUser("rwinch", 5431);
		var josh = createUser("jlong", 5432);
		var users = Stream.of(rob, josh)
			.collect(Collectors.toMap(User::getUsername, u -> u));
		return username -> {
			var user = users.getOrDefault(username, null);
			if (user == null) {
				throw new UsernameNotFoundException("could not find " + username + "!");
			}
			return user;
		};
	}

	private static User createUser(String name, Integer tenantId) {
		return new MultitenantUser(name, "pw", true, true, true, true, tenantId);
	}
}

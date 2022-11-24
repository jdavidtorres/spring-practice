package co.com.jdti.multitenantdatasource.utils;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;

public class MultitenantUser extends User {
	@Getter
	private final Integer tenantId;

	public MultitenantUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Integer tenantId) {
		super(username, PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, List.of(new SimpleGrantedAuthority("USER_")));
		this.tenantId = tenantId;
	}
}

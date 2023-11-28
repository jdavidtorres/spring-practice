package co.com.jdti.springmvcsecurity.services;

import co.com.jdti.springmvcsecurity.models.Account;
import co.com.jdti.springmvcsecurity.models.Role;
import co.com.jdti.springmvcsecurity.repositories.IAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

	private final IAccountRepository accountRepository;

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> accountOptional = accountRepository.findByUsername(username);
		if (accountOptional.isPresent()) {
			List<GrantedAuthority> roles = new ArrayList<>();
			for (Role role : accountOptional.get().getRoles()) {
				roles.add(new SimpleGrantedAuthority(role.getName()));
			}
			return new User(accountOptional.get().getUsername(), accountOptional.get().getPassword(), roles);
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
	}
}

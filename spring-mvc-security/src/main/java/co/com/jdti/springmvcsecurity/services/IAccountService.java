package co.com.jdti.springmvcsecurity.services;

import co.com.jdti.springmvcsecurity.models.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {

	Account save(Account account);
}

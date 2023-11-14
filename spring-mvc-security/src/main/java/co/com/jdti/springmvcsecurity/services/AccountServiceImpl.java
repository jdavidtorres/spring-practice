package co.com.jdti.springmvcsecurity.services;

import co.com.jdti.springmvcsecurity.models.Account;
import co.com.jdti.springmvcsecurity.repositories.IAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

	private final IAccountRepository accountRepository;

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}
}

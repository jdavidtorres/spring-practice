package co.com.jdti.springmvcsecurity.repositories;

import co.com.jdti.springmvcsecurity.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
}

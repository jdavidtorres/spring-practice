package co.com.jdti.walletapp.base;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IServiceHelper<T> {

	T create(T t);

	Optional<T> findById(String id);

	Page<T> findAll(int page);

	void delete(String id);
}

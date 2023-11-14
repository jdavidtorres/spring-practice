package co.com.jdti.springmvcsecurity.services;

import co.com.jdti.springmvcsecurity.models.Role;
import co.com.jdti.springmvcsecurity.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {

	private final RoleRepository roleRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
}

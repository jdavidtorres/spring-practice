package co.com.jdti.springmvcsecurity.repositories;

import co.com.jdti.springmvcsecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
}

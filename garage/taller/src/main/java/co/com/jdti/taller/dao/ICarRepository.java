package co.com.jdti.taller.dao;

import co.com.jdti.taller.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository extends JpaRepository<Car, String> {
}

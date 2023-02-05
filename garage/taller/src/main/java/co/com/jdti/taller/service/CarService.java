package co.com.jdti.taller.service;

import co.com.jdti.taller.dao.ICarRepository;
import co.com.jdti.taller.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

	private final ICarRepository iCarRepository;

	public List<Car> getCarList() {
		return iCarRepository.findAll();
	}

	public void addCar(Car car) {
		iCarRepository.save(car);
	}
}

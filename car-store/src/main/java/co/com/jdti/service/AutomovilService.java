package co.com.jdti.service;

import co.com.jdti.dao.AutomovilRepository;
import co.com.jdti.model.Automovil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AutomovilService {

	private final AutomovilRepository automovilRepository;

	public void agregarAutomovil(Automovil automovil) {
		automovilRepository.save(automovil);
	}

	public List<Automovil> findAll() {
		return automovilRepository.findAll();
	}

	public void delete(int id) {
		try {
			automovilRepository.deleteById(id);
		} catch (Exception e) {
			log.error("Error deleting");
		}
	}

	public Automovil getById(int idAuto) {
		return automovilRepository.findById(idAuto).get();
	}
}

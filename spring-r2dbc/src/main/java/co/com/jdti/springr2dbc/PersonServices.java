package co.com.jdti.springr2dbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonServices {

	private final PersonRepository personRepository;

	public Mono<Person> save(Person person) {
		log.info("Saving person: {}", person);
		return personRepository.save(person);
	}

	public Flux<Person> getPeople() {
		log.info("Getting people");
		return personRepository.findAll();
	}
}

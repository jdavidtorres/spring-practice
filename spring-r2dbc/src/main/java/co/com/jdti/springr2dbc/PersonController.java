package co.com.jdti.springr2dbc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

	private final PersonServices personServices;

	@PostMapping
	public Mono<Person> save(Person person) {
		return personServices.save(person);
	}

	@GetMapping
	public Flux<Person> getPeople() {
		return personServices.getPeople();
	}
}

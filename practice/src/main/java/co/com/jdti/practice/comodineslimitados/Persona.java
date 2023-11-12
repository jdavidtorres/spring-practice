package co.com.jdti.practice.comodineslimitados;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class Persona {
	private String name;
	private String lastName;

	public String fullName() {
		return name + " " + lastName;
	}

	@Override
	public String toString() {
		return "Persona: " +
			"name='" + name + '\'' + ", lastName='" + lastName + '\'';
	}
}

package co.com.jdti.practice.comodineslimitados;

import java.util.List;

public class UtilList {

	private UtilList() {
	}

	public static void showList(List<?> list) {
		for (Object object : list) {
			System.out.println(object);
		}
	}

	// Upper bounded wildcard
	public static void showPersonList(List<? extends Persona> theList) {
		for (Persona persona : theList) {
			System.out.println(persona.fullName());
		}
	}

	// Upper bounded wildcard
	public static void showTrainableList(List<? extends Trainable> theList) {
		for (Trainable trainable : theList) {
			trainable.train();
		}
	}

	// Lower bounded wildcard
	public static void addSportsmanToList(List<? super Sportsman> theList, String name, String lastName, int energy) {
		Sportsman sportsman = new Sportsman(name, lastName, energy);
		theList.add(sportsman);
	}
}

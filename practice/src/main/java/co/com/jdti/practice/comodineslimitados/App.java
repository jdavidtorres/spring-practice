package co.com.jdti.practice.comodineslimitados;

import java.util.ArrayList;

public class App {

	public static void main(String[] args) {
		Persona p = new Persona("Juan", "Perez");
		Sportsman bolt = new Sportsman("Usain", "Bolt", 100);
		Sportsman roger = new Sportsman("Roger", "Federer", 70);
		SoccerPlayer messi = new SoccerPlayer("Lionel", "Messi", 80, 10);
		SoccerPlayer cr = new SoccerPlayer("Cristiano", "Ronaldo", 85, 7);

		ArrayList<Sportsman> list = new ArrayList<>();
		list.add(cr);

		UtilList.addSportsmanToList(list, "Juan", "Perez", 50);
		UtilList.showPersonList(list);
	}
}

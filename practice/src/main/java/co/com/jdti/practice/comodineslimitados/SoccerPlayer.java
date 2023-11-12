package co.com.jdti.practice.comodineslimitados;

public class SoccerPlayer extends Sportsman {
	private int dorsal;

	public SoccerPlayer(String name, String lastName, int energy, int dorsal) {
		super(name, lastName, energy);
		this.dorsal = dorsal;
	}

	public void kick() {
		System.out.println("Kicking...");
	}

	@Override
	public String toString() {
		return super.toString() + ", dorsal=" + dorsal;
	}
}

package co.com.jdti.practice.comodineslimitados;

public class Sportsman extends Persona implements Trainable {
	private int energy;

	public Sportsman(String name, String lastName, int energy) {
		super(name, lastName);
		this.energy = energy;
	}

	public void rest() {
		this.energy = 100;
	}

	@Override
	public String toString() {
		return
			super.toString() + ", energy=" + energy;
	}

	@Override
	public void train() {
		System.out.println("Training...");
	}
}

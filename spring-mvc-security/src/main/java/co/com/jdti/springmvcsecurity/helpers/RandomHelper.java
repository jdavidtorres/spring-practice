package co.com.jdti.springmvcsecurity.helpers;

public class RandomHelper {

	public static String generate(int length) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder random = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * chars.length());
			random.append(chars.charAt(index));
		}
		return random.toString();
	}
}

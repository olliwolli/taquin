package game.rubikcube;

import java.util.Random;

public enum Color {
	RED, BLUE, YELLOW, WHITE, GREEN, ORANGE, NONE;

	public static Color getRandom() {
		Random r = new Random();
		Color[] c = Color.values();
		return c[r.nextInt(c.length - 1)];
	}
}

package framework;

/**
 * Just to provide a toString() with the
 * infinity sign
 */
public class AlphaBetaWrapper {
	private int value;

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		if (value == Integer.MAX_VALUE)
			return "ꝏ";
		else if (value == -Integer.MAX_VALUE)
			return "-ꝏ";
		return Integer.toString(value);
	}
}

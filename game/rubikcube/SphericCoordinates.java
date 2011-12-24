package game.rubikcube;

public class SphericCoordinates {
	public double phi;
	public double theta;

	public static Position FRONT_NORTHWEST = new Position(-5, -85);
	public static Position FRONT_NORTH = new Position(0, -85);
	public static Position FRONT_NORTHEAST = new Position(5, -85);
	public static Position FRONT_EAST = new Position(5, -90);
	public static Position FRONT_CENTER = new Position(0, 0);
	public static Position FRONT_WEST = new Position(-5, -90);
	public static Position FRONT_SOUTHEAST = new Position(5, -95);
	public static Position FRONT_SOUTH = new Position(0, -95);
	public static Position FRONT_SOUTHWEST = new Position(-5, -95);

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(phi);
		sb.append("," + theta);
		return sb.toString();
	}

	public SphericCoordinates(double phi, double theta) {
		super();
		this.phi = phi;
		this.theta = theta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(phi);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(theta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SphericCoordinates other = (SphericCoordinates) obj;
		if (Double.doubleToLongBits(phi) != Double.doubleToLongBits(other.phi))
			return false;
		if (Double.doubleToLongBits(theta) != Double
				.doubleToLongBits(other.theta))
			return false;
		return true;
	}
}

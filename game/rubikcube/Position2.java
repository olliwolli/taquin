package game.rubikcube;

import javax.vecmath.Vector3d;

public class Position2 extends Vector3d implements Comparable<Position2> {
	private static final long serialVersionUID = 1L;

	public Position2(double x, double y, double z) {
		super(x, y, z);
	}

	public Position2(double phi, double theta) {
		double phiRad = Math.toRadians(phi);
		double thetaRad = Math.toRadians(theta);

		x = Math.sin(phiRad) * Math.cos(thetaRad);
		y = Math.sin(phiRad) * Math.sin(thetaRad);
		z = Math.cos(phiRad);
	}

	public Position2(SphericCoordinates sc) {
		this(sc.phi, sc.theta);
	}

	public SphericCoordinates toSpheric() {
		double phi, theta;
		theta = Math.atan2(y, x);

		// theta = Math.acos(z / (Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) +
		// Math.pow(z, 2)) ));
		phi = Math.PI / 2
				- Math.atan(z / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))));

		phi = Math.round(Math.toDegrees(phi));
		theta = Math.round(Math.toDegrees(theta));
		return new SphericCoordinates(phi, theta);
	}

	public void rotateZ(double angle) {
		double nx, ny;
		angle = Math.toRadians(angle);
		nx = x * Math.cos(angle) - y * Math.sin(angle);
		ny = y * Math.cos(angle) + x * Math.sin(angle);
		x = nx;
		y = ny;
	}

	public void rotateX(double angle) {
		double ny, nz;
		angle = Math.toRadians(angle);
		ny = y * Math.cos(angle) - z * Math.sin(angle);
		nz = z * Math.cos(angle) + y * Math.sin(angle);
		y = ny;
		z = nz;
	}

	public void rotateY(double angle) {
		double nx, nz;
		angle = Math.toRadians(angle);
		nx = x * Math.cos(angle) + z * Math.sin(angle);
		nz = z * Math.cos(angle) - x * Math.sin(angle);
		x = nx;
		z = nz;
	}

	@Override
	public int compareTo(Position2 o) {
		// SphericCoordinates sc1 = toSpheric();
		// SphericCoordinates sc2 = o.toSpheric();
		//
		// if(sc1.phi == sc2.phi && sc1.theta == sc2.theta)
		// return 0;
		// return -1;
		// double treshhold = 0.001;
		// if(Math.abs(x-o.x) < treshhold && Math.abs(y-o.y) < treshhold &&
		// Math.abs(z-o.z) < treshhold){

		if (x == o.x && y == o.y && z == o.z) {
			return 0;
		}
		return -1;
	}

	public static int turnDistance(Position2 pos1, Position2 pos2) {
		return turnDistance(pos1.toSpheric().phi, pos1.toSpheric().theta, pos2
				.toSpheric().phi, pos2.toSpheric().theta);
	}

	/*
	 * calculates minimal number of turns necessary to move from pos1 to pos2
	 * pos1 and pos2 can be inverted and the resulting value would be the same
	 * 
	 * @return number of turns needed
	 */
	public static int turnDistance(double x1, double y1, double x2, double y2) {
		double distanceX, distanceY;
		boolean turnsX, turnsY;

		distanceX = x1 - x2;
		distanceY = y1 - y2;

		turnsX = needsTurn(distanceX);
		turnsY = needsTurn(distanceY);

		if (!turnsX && !turnsY)
			return 0;
		else if (Math.abs(distanceX) > Math.abs(distanceY)) {
			if (Math.abs(distanceX) > 10)
				return turnDistance(x1 < 0 ? x1 + 90 : x1 - 90, y1, x2, y2) + 1;
			else
				return turnDistance(x1 < 0 ? x1 + 10 : x1 - 10, y1, x2, y2) + 1;
		} else {
			if (Math.abs(distanceY) > 10)
				return turnDistance(x1, y1 < 0 ? y1 + 90 : y1 - 90, x2, y2) + 1;
			else
				return turnDistance(x1, y1 < 0 ? y1 + 10 : y1 - 10, x2, y2) + 1;
		}
	}

	private static boolean needsTurn(double distanceAngle) {
		if (distanceAngle == 0)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		System.out.println("CALLED HASH");
		final int prime = 31;
		int result;
		SphericCoordinates sc1 = toSpheric();
		long temp;
		temp = Double.doubleToLongBits(sc1.phi);
		result = prime + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sc1.theta);
		result = prime + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position2 other = (Position2) obj;
		if (compareTo(other) != 0)
			return false;
		return true;
	}

	public String toString() {
		return toSpheric().toString();
	}

}

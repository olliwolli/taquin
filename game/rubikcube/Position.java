package game.rubikcube;

import javax.vecmath.Vector3d;

public class Position {

	public double phi;
	public double theta;
	public double x = 0;
	public double y = 0;
	public double z = 0;
	/* x = left/right y = front/back */

	/* sinus dispersion function */
	// public static Position TOP_CENTER = new Position(0, 0, 0);
	public static Position TOP_NORTHWEST = new Position(-5, 5);
	public static Position TOP_NORTH = new Position(0, 5);
	public static Position TOP_NORTHEAST = new Position(5, 5);
	public static Position TOP_EAST = new Position(5, 0);
	public static Position TOP_CENTER = new Position(0, 0);
	public static Position TOP_WEST = new Position(-5, 0);
	public static Position TOP_SOUTHEAST = new Position(5, -5);
	public static Position TOP_SOUTH = new Position(0, -5);
	public static Position TOP_SOUTHWEST = new Position(-5, -5);

	public static Position BOTTOM_NORTHWEST = new Position(-5, 185);
	public static Position BOTTOM_NORTH = new Position(0, 185);
	public static Position BOTTOM_NORTHEAST = new Position(5, 185);
	public static Position BOTTOM_EAST = new Position(5, 180);
	public static Position BOTTOM_CENTER = new Position(0, 180);
	public static Position BOTTOM_WEST = new Position(-5, 180);
	public static Position BOTTOM_SOUTHEAST = new Position(5, -175);
	public static Position BOTTOM_SOUTH = new Position(0, -175);
	public static Position BOTTOM_SOUTHWEST = new Position(-5, -175);

	public static Position FRONT_NORTHWEST = new Position(-5, -85);
	public static Position FRONT_NORTH = new Position(0, -85);
	public static Position FRONT_NORTHEAST = new Position(5, -85);
	public static Position FRONT_EAST = new Position(5, -90);
	public static Position FRONT_CENTER = new Position(0, -90);
	public static Position FRONT_WEST = new Position(-5, -90);
	public static Position FRONT_SOUTHEAST = new Position(5, -95);
	public static Position FRONT_SOUTH = new Position(0, -95);
	public static Position FRONT_SOUTHWEST = new Position(-5, -95);

	public static Position BACK_NORTHWEST = new Position(-5, 95);
	public static Position BACK_NORTH = new Position(0, 95);
	public static Position BACK_NORTHEAST = new Position(5, 95);
	public static Position BACK_EAST = new Position(5, 90);
	public static Position BACK_CENTER = new Position(0, 90);
	public static Position BACK_WEST = new Position(-5, 90);
	public static Position BACK_SOUTHEAST = new Position(5, 85);
	public static Position BACK_SOUTH = new Position(0, 85);
	public static Position BACK_SOUTHWEST = new Position(-5, 85);

	public static Position LEFT_NORTHWEST = new Position(-95, 5);
	public static Position LEFT_NORTH = new Position(-90, 5);
	public static Position LEFT_NORTHEAST = new Position(-85, 5);
	public static Position LEFT_EAST = new Position(-85, 0);
	public static Position LEFT_CENTER = new Position(-90, 0);
	public static Position LEFT_WEST = new Position(-95, 0);
	public static Position LEFT_SOUTHEAST = new Position(-85, -5);
	public static Position LEFT_SOUTH = new Position(-90, -5);
	public static Position LEFT_SOUTHWEST = new Position(-95, -5);

	public static Position RIGHT_NORTHWEST = new Position(85, 5);
	public static Position RIGHT_NORTH = new Position(90, 5);
	public static Position RIGHT_NORTHEAST = new Position(95, 5);
	public static Position RIGHT_EAST = new Position(95, 0);
	public static Position RIGHT_CENTER = new Position(90, 0);
	public static Position RIGHT_WEST = new Position(85, 0);
	public static Position RIGHT_SOUTHEAST = new Position(95, -5);
	public static Position RIGHT_SOUTH = new Position(90, -5);
	public static Position RIGHT_SOUTHWEST = new Position(85, -5);

	public Position(int phi, int theta) {
		super();
		this.phi = phi;
		this.theta = theta;
	}

	public Position(double x, double y, double z) {
		phi = Math.atan2(y, x);
		theta = Math.PI / 2
				- Math.atan(z / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))));

		phi = Math.round(Math.toDegrees(phi));
		theta = Math.round(Math.toDegrees(theta));
	}

	public static int turnDistance(Position pos1, Position pos2) {
		return turnDistance(pos1.phi, pos1.theta, pos2.phi, pos2.theta);
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
		/*
		 * was: get number of turns necessary int normDistance =
		 * Math.abs(distanceAngle); if(normDistance > 0 && normDistance <= 10)
		 * return 1; else if(normDistance > 10 && normDistance <= 90) return 1;
		 * else if(normDistance > 90 && normDistance <= 180) return 2; else
		 * throw new IllegalArgumentException("Distance angle is illegal");
		 */
	}

	public void normalize() {
		/* cut angles bigger than 180° */
		phi %= 180;
		theta %= 180;

		/*
		 * Two cases which overlap: x=0, y=0 and x=0, y=0 -> the same by math
		 * x=180, y=0 and x=0, y=180 -> swap and -180,0 and 0,-180
		 */
		if (phi == -180)
			phi *= -1;
		if (theta == -180)
			theta *= -1;
		if ((phi == 0 && theta == 180)) {
			phi = 180;
			theta = 0;
		}
	}

	public int compareTo(Position position) {
		if (phi < position.phi && theta < position.theta)
			return -1;
		if (phi < position.phi && theta > position.theta)
			return -1;
		if (phi == position.phi && theta == position.theta)
			return 0;
		return 1;
	}

	public Position rotateZ(double angle) {
		Vector3d vec = this.toCartesian();
		double nx, ny;
		nx = vec.x * Math.cos(angle) - vec.y * Math.sin(angle);
		ny = vec.y * Math.sin(angle) + vec.x * Math.sin(angle);
		return new Position(nx, ny, vec.z);
	}

	public Position rotateX(double angle) {
		Vector3d vec = this.toCartesian();
		double ny, nz;
		ny = vec.y * Math.cos(angle) - vec.z * Math.sin(angle);
		nz = vec.z * Math.cos(angle) + vec.y * Math.sin(angle);
		return new Position(vec.x, ny, nz);
	}

	public Position rotateY(double angle) {
		Vector3d vec = this.toCartesian();
		double nx, nz;
		nx = vec.x * Math.cos(angle) + vec.z * Math.sin(angle);
		nz = vec.z * Math.cos(angle) - vec.x * Math.sin(angle);
		return new Position(nx, vec.y, nz);
	}

	public Vector3d toCartesian() {
		double phiRad = Math.toRadians(phi);
		double thetaRad = Math.toRadians(theta);
		return new Vector3d(Math.sin(thetaRad) * Math.cos(phiRad), Math
				.sin(thetaRad)
				* Math.sin(phiRad), Math.cos(thetaRad));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("phi: " + phi + "\n");
		sb.append("theta: " + theta);
		return sb.toString();
	}
}

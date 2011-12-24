package game.rubikcube;

import javax.vecmath.Vector3d;

public class TestAngle extends Vector3d {

	public TestAngle(double phi, double theta) {
		double phiRad = Math.toRadians(phi);
		double thetaRad = Math.toRadians(theta);

		x = Math.sin(thetaRad) * Math.cos(phiRad);
		y = Math.sin(thetaRad) * Math.sin(phiRad);
		z = Math.cos(thetaRad);

	}

	public SphericCoordinates toSpheric() {
		double phi, theta;
		phi = Math.atan2(y, x);
		theta = Math.PI / 2
				- Math.atan(z / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))));

		phi = Math.toDegrees(phi);
		theta = Math.toDegrees(theta);

		phi = Math.round(phi);
		theta = Math.round(theta);

		return new SphericCoordinates(phi, theta);
	}
}

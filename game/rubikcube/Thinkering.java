package game.rubikcube;


public class Thinkering {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CubeInitializer.initialize();

		// initTest();

		// rotationTest();
	}

	public static void initTest() {
		int phi = -10;
		int theta = -10;

		System.out.println("Phi: " + phi + " Theta: " + theta);
		TestAngle pos = new TestAngle(phi, theta);
		System.out.println("x: " + pos.x + " y: " + pos.y + " z: " + pos.z);
		System.out.println("Phi: " + pos.toSpheric().phi + " Theta: "
				+ pos.toSpheric().theta);
	}

	public static void rotationTest() {
		double rotZ = 90;
		double rotX = 90;
		double rotY = 90;

		Position2 p = new Position2(1, 0, 0);
		// System.out.println("Original:   \n"+p+"\n"+"Spheric: "+p.toSpheric()+"\n");
		// System.out.println("Z-Rotation: \n"+p.rotateZ(rotZ)+"\n"+"Spheric: "+p.rotateZ(rotZ).toSpheric()+"\n");
		// System.out.println("X-Rotation: \n"+p.rotateX(rotX)+"\n"+"Spheric: "+p.rotateX(rotX).toSpheric()+"\n");
		// System.out.println("Y-Rotation: \n"+p.rotateY(rotY)+"\n"+"Spheric: "+p.rotateY(rotY).toSpheric()+"\n");
	}

}

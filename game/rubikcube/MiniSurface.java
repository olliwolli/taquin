package game.rubikcube;

public class MiniSurface implements Comparable<MiniSurface> {

	public Position2 position;
	public Position2 solutionPosition;

	/* FACES: 0: FRONT, 1: BACK, 2: LEFT, 3: RIGHT, 4: BOTTOM, 5: TOP */
	private static int[] faceOrientationX = { 0, 0, -90, 90, 180, 0 };
	private static int[] faceOrientationY = { 90, -90, 0, 0, 0, 0 };
	private static int[] faceOrientationZ = { 0, 0, 0, 0, 0, 0 };

	/* FACES: 0: DOWN, 1: UP, 2: LEFT, 3: RIGHT, */
	private static int[] faceRotationX = { 0, 0, -90, 90 };
	private static int[] faceRotationY = { 90, -90, 0, 0 };
	private static int[] faceRotationZ = { 0, 0, 0, 0 };

	public MiniSurface(Position2 position, Position2 solutionPosition) {
		super();
		this.position = position;
		this.solutionPosition = solutionPosition;
	}

	public MiniSurface(double phi, double theta, int face) {
		this.solutionPosition = rotateToFace(face, new Position2(phi, theta));
	}

	public MiniSurface(double phi, double theta, int face, int face2) {
		this.solutionPosition = rotateToFace(face, new Position2(phi, theta));
		this.solutionPosition = rotateRelative(face2, solutionPosition);
	}

	// public MiniSurface(double x, double y, double z, int face){
	// this.solutionPosition = rotateToFace(face, new Position2(x, y, z));
	// }
	//	
	// public MiniSurface(double x, double y, double z, int face, int face2){
	// this.solutionPosition = rotateToFace(face, new Position2(x, y, z));
	// this.solutionPosition = rotateRelative(face2, solutionPosition);
	// }
	//	
	private static Position2 rotateToFace(int face, Position2 pos) {
		pos.rotateX(faceOrientationX[face]);
		pos.rotateY(faceOrientationY[face]);
		pos.rotateZ(faceOrientationZ[face]);
		return pos;
	}

	private static Position2 rotateRelative(int face, Position2 pos) {
		pos.rotateX(faceRotationX[face]);
		pos.rotateY(faceRotationY[face]);
		pos.rotateZ(faceRotationZ[face]);
		return pos;
	}

	public void setPosition(Position2 position) {
		this.position = position;
	}

	public boolean isInFinalState() {
		if (position.compareTo(solutionPosition) == 0)
			return true;
		return false;
	}

	public int distanceFromFinalState() {
		return Position2.turnDistance(position, solutionPosition);
	}

	@Override
	public int compareTo(MiniSurface o) {
		return solutionPosition.compareTo(o.solutionPosition);
	}

	public String toString() {
		if (solutionPosition != null)
			return solutionPosition.toSpheric().toString();
		return "";
	}

}

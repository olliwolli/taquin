package game.rubikcube;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CubeInitializer {

	/* x,y,z */
	private static double[][] faceCoord2 = { { -1, 1, 1 }, { 0, 1, 1 },
			{ 1, 1, 1 }, { -1, 0, 1 }, { 0, 0, 1 }, { 1, 0, 1 }, { -1, -1, 1 },
			{ 0, -1, 1 }, { 1, -1, 1 } };

	private static double[][] faceCoord = { { 348, 168 }, { 2, 168 },
			{ 12, 168 }, { 348, 2 }, { 2, 2 }, { 12, 2 }, { 348, 12 },
			{ 2, 12 }, { 12, 12 } };

	/*
	 * FRONT FACE
	 * 
	 * looking from the front face on the cube.
	 * 
	 * X-Axis points from the front middle piece to the back Z-Axis points from
	 * up to down Y-Axis points from left to right
	 * 
	 * Rotations: Left/Right: Z-Axis Up/Down: X-Axis ClockWise/CounterClockwise:
	 * Y-Axis
	 */

	/* int face */
	public static List<MiniSurface> getRotationUnit(int face) {
		List<MiniSurface> group = new ArrayList<MiniSurface>();

		//		
		for (int i = 0; i < faceCoord.length; i++) {
			// System.out.println("Phi: "+faceCoord[i][0]+" Theta: "+faceCoord[i][1]);
			group.add(new MiniSurface(faceCoord[i][0], faceCoord[i][1], face));
			// Position2 pos = group.get(group.size()-1).solutionPosition;
			// System.out.println("Phi: "+pos.toSpheric().phi+" Theta: "+pos.toSpheric().theta+"\n");
		}

		/* UP strip */
		group.add(new MiniSurface(faceCoord[6][0], faceCoord[6][1], face, 1));
		group.add(new MiniSurface(faceCoord[7][0], faceCoord[7][1], face, 1));
		group.add(new MiniSurface(faceCoord[8][0], faceCoord[8][1], face, 1));

		/* DOWN strip */
		group.add(new MiniSurface(faceCoord[0][0], faceCoord[0][1], face, 0));
		group.add(new MiniSurface(faceCoord[1][0], faceCoord[1][1], face, 0));
		group.add(new MiniSurface(faceCoord[2][0], faceCoord[2][1], face, 0));

		/* Right strip */
		group.add(new MiniSurface(faceCoord[0][0], faceCoord[0][1], face, 3));
		group.add(new MiniSurface(faceCoord[3][0], faceCoord[3][1], face, 3));
		group.add(new MiniSurface(faceCoord[6][0], faceCoord[6][1], face, 3));

		/* Left strip */
		group.add(new MiniSurface(faceCoord[2][0], faceCoord[2][1], face, 2));
		group.add(new MiniSurface(faceCoord[5][0], faceCoord[5][1], face, 2));
		group.add(new MiniSurface(faceCoord[8][0], faceCoord[8][1], face, 2));

		return group;
	}

	public static void initialize() {
		Set<MiniSurface> set = new TreeSet<MiniSurface>();
		/* FACES: 0: FRONT, 1: BACK, 2: LEFT, 3: RIGHT, 4: BOTTOM, 5: TOP */
		List<MiniSurface> topGroup = getRotationUnit(5);
		List<MiniSurface> frontGroup = getRotationUnit(0);
		List<MiniSurface> backGroup = getRotationUnit(1);
		List<MiniSurface> leftGroup = getRotationUnit(2);
		List<MiniSurface> rightGroup = getRotationUnit(3);
		List<MiniSurface> bottomGroup = getRotationUnit(4);

		set.addAll(topGroup);
		set.addAll(frontGroup);
		// set.addAll(bottomGroup);
		// set.addAll(backGroup);

		// set.addAll(leftGroup);
		// set.addAll(rightGroup);

		int i = 0;
		for (MiniSurface ms : topGroup) {
			i++;
			System.out.print(ms.solutionPosition.toSpheric() + "  \t");
			if (i % 3 == 0)
				System.out.println();

		}
		System.out.println();

		for (MiniSurface ms : frontGroup) {
			i++;
			System.out.print(ms.solutionPosition.toSpheric() + "  \t");
			if (i % 3 == 0)
				System.out.println();
		}

		System.out.println("\nFront size " + frontGroup.size());
		System.out.println("Top size " + topGroup.size());
		System.out.println("Set size " + set.size());
	}
}

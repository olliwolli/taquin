package game.rubikcube;

import org.apache.commons.collections15.Predicate;

public class MoveFilter implements Predicate<MiniSurface> {
	private int xAxis;
	private int yAxis;

	@Override
	public boolean evaluate(MiniSurface minisurface) {
		Position2 pos = minisurface.position;

		/*
		 * Middle surface ( -5, 5), ( 0, 5), ( 5, 5) ( -5, 0), ( 0, 0), ( 5, 0)
		 * ( -5, -5), ( 0, -5), ( 5, -5)
		 */

		/* Middle piece (e.g. (0,0)) -> not moveable */
		if (pos.xAngle == pos.yAngle)
			return false;

		/* one of the middle surface pieces */
		if (areaCheck(pos.xAngle, xAxis, 5)) {
			if (areaCheck(pos.yAngle, yAxis, 5)) {
				return true;
			}
		}

		/*
		 * axis (0, 0) -> (-85, 0), (-85, 5), (-85, -5) LEFT ( 85, 0), ( 85, 5),
		 * ( 85, -5) RIGHT ( 0,-85), ( 5,-85), ( -5,-85) FRONT ( 0, 85), ( 5,
		 * 85), ( -5, 85) BACK
		 */
		if (areaCheck(pos.xAngle, xAxis, 85)) {
			if (areaCheck(pos.yAngle, yAxis, 85)) {
				return true;
			}
		}

		return false;
	}

	/* check if coord is in radius of origin or the origin itself */
	public boolean areaCheck(int coord, int origin, int radius) {
		if (coord == origin || coord == origin - radius
				|| coord == origin + radius)
			return true;
		return false;
	}

	public MoveFilter setxAxis(int xAxis) {
		this.xAxis = xAxis;
		return this;
	}

	public MoveFilter setyAxis(int yAxis) {
		this.yAxis = yAxis;
		return this;
	}
}

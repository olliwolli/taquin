package game.rubikcube;

import game.IGame;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;

public class RubikCube implements IGame {

	private List<MiniSurface> state = new ArrayList<MiniSurface>(); /*
																	 * TODO: add
																	 * initial
																	 * capacity
																	 */
	private int[] axisis = { 0, 90, -90, 180 }; /* (180,180) invalid */

	public RubikCube() {
	}

	@Override
	public int cost(IGame parent) {
		return 1;
	}

	@Override
	public int evaluation() {
		int cost = 0;
		for (MiniSurface miniSurface : state) {
			cost += miniSurface.distanceFromFinalState();
		}
		return cost;
	}

	@Override
	public List<IGame> possibleNextGameStates() {
		List<MiniSurface> affectedSurfaces;
		MoveFilter filter = new MoveFilter();
		int sense = 1; /* 1: clockwise, -1: counterclockwise */

		for (int x : axisis) {
			for (int y : axisis) {
				filter.setxAxis(x).setyAxis(y);
				affectedSurfaces = new ArrayList<MiniSurface>(CollectionUtils
						.predicatedCollection(state, filter));

			}
		}

		return null;
	}

	@Override
	public boolean gameOver() {
		/* TODO: reimplement with position.final? */
		// if(compareTo(FINALSTATE)==0)
		// return true;
		return false;
	}

	@Override
	public Object getState() {
		return state;
	}

	@Override
	public int compareTo(IGame anotherGameState) {
		// Position2[] otherstate = (Position2[])anotherGameState.getState();
		// int compTmp;
		//		
		// for(int i =0; i<state.size(); i++){
		// compTmp= state.get(i).compareTo(otherstate[i]);
		// if(compTmp!=0)
		// return compTmp;
		// }
		return 0;
	}
}

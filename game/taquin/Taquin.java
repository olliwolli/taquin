package game.taquin;

import game.IGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * Implementation of the taquin game. It supports variable field sizes. The
 * empty field is defined as -1
 */
public class Taquin extends Observable implements IGame {
	private final static int emptyField = -1;
	public static Taquin finalState;

	private int[][] field;

	public Taquin(int size, int randomFactor, boolean solvable) {
		if (solvable)
			field = generateSolvableRandomState(size, randomFactor);
		else
			field = generateRandomState(size, randomFactor);

		finalState = new Taquin(generateFinalState(size));
	}

	public Taquin(int[][] state) {
		// field = copyTaquin(state);
		field = state;
	}

	/* generate a final game state for a given game size */
	public static int[][] generateFinalState(int size) {
		int[][] finalField = new int[size][size];
		int i = 1;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (i < size * size) {
					finalField[x][y] = i;
					i++;
				} else {
					finalField[x][y] = emptyField;
				}
			}
		}
		return finalField;
	}

	/* find the position of a certain tile in the game field */
	private static int[] findValue(int[][] field, int value) {
		int[] values = new int[2];
		for (int x = 0; x < field.length; x++)
			for (int y = 0; y < field.length; y++)
				if (field[x][y] == value) {
					values[0] = x;
					values[1] = y;
					return values;
				}
		return values;
	}

	/* as above but non-static */
	private int[] findValue(int value) {
		return findValue(this.field, value);
	}

	/* clone the game state */
	public static int[][] copyTaquin(int[][] old) {
		int[][] newT = new int[old.length][old.length];
		for (int x = 0; x < old.length; x++)
			for (int y = 0; y < old.length; y++)
				newT[x][y] = old[x][y];
		return newT;
	}

	public IGame clone() {
		return new Taquin(copyTaquin(this.field));
	}

	/* exchange two tiles */
	public static int[][] swapFields(int[][] state, int oneX, int oneY,
			int twoX, int twoY) {
		int[][] newState = copyTaquin(state);

		int oneValue = state[oneX][oneY];
		int twoValue = state[twoX][twoY];
		newState[oneX][oneY] = twoValue;
		newState[twoX][twoY] = oneValue;
		return newState;
	}

	/* exchange two tiles of a taquin game */
	public static int[][] swapFields(Taquin state, int oneX, int oneY,
			int twoX, int twoY) {
		return swapFields(state.field, oneX, oneY, twoX, twoY);
	}

	/* return the cost */
	public int cost(IGame parent) {
		return 1;
	}

	/*
	 * generate a random state from the solved game This function exchanges two
	 * random tiles "randomFactor" times.
	 */
	private static int[][] generateRandomState(int size, int randomFactor) {
		int[][] randomState = generateFinalState(size);
		Random r = new Random();
		/* swapping randomly */
		for (; randomFactor > 0; randomFactor--) {
			randomState = swapFields(randomState, r.nextInt(size), r
					.nextInt(size), r.nextInt(size), r.nextInt(size));
		}
		return randomState;
	}

	/*
	 * generate a random state from the solved game this function does
	 * "randomFactor" times a random move with the empty tile
	 */
	private static int[][] generateSolvableRandomState(int size,
			int randomFactor) {
		int[][] randomState = generateFinalState(size);

		List<int[][]> gs = new ArrayList<int[][]>();
		Random r = new Random();
		/* swapping randomly */
		for (; randomFactor > 0; randomFactor--) {
			int[] emptyCoord = findValue(randomState, Taquin.emptyField);
			if (emptyCoord[0] > 0)
				gs.add(swapFields(randomState, emptyCoord[0], emptyCoord[1],
						emptyCoord[0] - 1, emptyCoord[1]));
			if (emptyCoord[0] < randomState.length - 1)
				gs.add(swapFields(randomState, emptyCoord[0], emptyCoord[1],
						emptyCoord[0] + 1, emptyCoord[1]));
			if (emptyCoord[1] > 0)
				gs.add(swapFields(randomState, emptyCoord[0], emptyCoord[1],
						emptyCoord[0], emptyCoord[1] - 1));
			if (emptyCoord[1] < randomState.length - 1)
				gs.add(swapFields(randomState, emptyCoord[0], emptyCoord[1],
						emptyCoord[0], emptyCoord[1] + 1));

			randomState = gs.get(r.nextInt(gs.size()));
			gs.clear();
		}

		return randomState;
	}

	/*
	 * compares two game-states. this function is important in order to support
	 * sorted collections of game-states
	 * 
	 * @see game.IGame#compareTo(game.IGame)
	 */
	@Override
	public int compareTo(IGame anotherGameState) {
		int[][] otherstate = (int[][]) anotherGameState.getState();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				if (field[i][j] > otherstate[i][j])
					return 1;
				else if (field[i][j] < otherstate[i][j])
					return -1;
			}
		}
		return 0;
	}

	/*
	 * this evaluation method returns the number of misplaced tiles
	 */
	private int evaluationSimple() {
		int distance = 0;
		if (!(finalState instanceof Taquin))
			return -1;

		Taquin t = (Taquin) finalState;
		if (t.field.length != field.length)
			return -1;

		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field.length; y++) {
				if (t.field[x][y] != field[x][y])
					distance++;
			}
		}
		return distance;
	}

	/* return the distance of a tile from its final position */
	public int getDistance(int value, int x, int y) {
		int finalPostionX = (value - 1) % (field.length);
		int finalPostionY = (value - 1 - finalPostionX) / field.length;
		int distance = 0;
		/* swap x and y */
		distance += Math.abs(finalPostionX - x);
		distance += Math.abs(finalPostionY - y);
		// System.out.println("Distance of "+value+" is "+distance);
		return distance;
	}

	/* evaluation function based on distance */
	private int evaluationAdvanced() {
		int cost = 0;
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field.length; y++) {
				// if(field[x][y]!=emptyField)
				cost += getDistance(field[x][y], x, y);
			}
		}
		return cost;
	}

	@Override
	public int evaluation() {
		return evaluationAdvanced();
	}

	/* tells if the game is over (final state reached) */
	@Override
	public boolean gameOver() {
		if (compareTo(finalState) == 0)
			return true;
		return false;
	}

	/* returns a list of possible next game states */
	@Override
	public List<IGame> possibleNextGameStates() {
		List<IGame> gameStates = new ArrayList<IGame>();
		int[] emptyCoord = findValue(Taquin.emptyField);
		if (emptyCoord[0] > 0)
			gameStates.add(new Taquin(swapFields(field, emptyCoord[0],
					emptyCoord[1], emptyCoord[0] - 1, emptyCoord[1])));
		if (emptyCoord[0] < field.length - 1)
			gameStates.add(new Taquin(swapFields(field, emptyCoord[0],
					emptyCoord[1], emptyCoord[0] + 1, emptyCoord[1])));
		if (emptyCoord[1] > 0)
			gameStates.add(new Taquin(swapFields(field, emptyCoord[0],
					emptyCoord[1], emptyCoord[0], emptyCoord[1] - 1)));
		if (emptyCoord[1] < field.length - 1)
			gameStates.add(new Taquin(swapFields(field, emptyCoord[0],
					emptyCoord[1], emptyCoord[0], emptyCoord[1] + 1)));
		return gameStates;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field.length; x++) {
				sb.append(field[x][y] + " ");
			}
			sb.append("\n");
		}
		return sb.substring(0, sb.length() - 1);
	}

	/* returns the game-state */
	@Override
	public Object getState() {
		return field;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Taquin other = (Taquin) obj;
		if (!(0 == compareTo(other)))
			return false;
		return true;
	}
}

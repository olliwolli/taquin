package game;

import java.util.List;

/**
 * Interface describing a game.
 */
public interface IGame {
	public int evaluation();

	public boolean gameOver();

	public int compareTo(IGame anotherGameState);

	public List<IGame> possibleNextGameStates();

	public int cost(IGame parent);

	public Object getState();
}

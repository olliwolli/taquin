package main;

import framework.AStar;
import framework.Node;
import framework.TwoPlayerGame;
import game.GameObserver;
import game.IGame;
import game.grundy.Grundy;
import game.grundy.GrundyNewRule;
import game.taquin.Taquin;

/**
 * Executable to test the games Uncomment lines in the main method to run a
 * game.
 */
public class GameTester {

	public static void main(String[] args) {
		// GameObserver go= new GameObserver();
		// Node.addObserver(go);
		// grundyGameTree();
		// grundyNegamaxAlphaBeta();
		// grundyGame();
		// grundyNewRule();
		taquinTest();
	}

	private static void taquinTest() {
		Taquin taquin = new Taquin(3, 50, true);
		System.out.println("FinalState:\n" + Taquin.finalState.toString());
		System.out.println("Original:\n" + taquin);
		AStar a = new AStar(taquin);
		Node solution = a.astar(null);
		if (solution != null)
			System.out.println(solution.toBottomUpString());
		else
			System.out.println("There is no solution");
	}

	private static void grundyNegamaxAlphaBeta() {
		System.out.println("Alpha Beta Pruning with Negamax");

		IGame firstState = new Grundy(5);
		TwoPlayerGame a = new TwoPlayerGame(firstState, null);
		TwoPlayerGame.addPlayer("A");
		TwoPlayerGame.addPlayer("B");

		/* run the game using alpha beta pruning and negamax */
		a.alphaBetaSearch(true);

		/*
		 * determine whether there is a player who can always win and name him
		 * if so
		 */
		System.out.println(a.resultToString());
		System.out.println(a.toTopDownToString());
	}

	public static void grundyGameTree() {

		System.out.println("Without alpha beta pruning");
		/* make a game tree without pruning */

		IGame firstState = new Grundy(7);
		TwoPlayerGame a = new TwoPlayerGame(firstState);
		TwoPlayerGame.addPlayer("A");
		TwoPlayerGame.addPlayer("B");
		a.play();
		System.out.println(a.toTopDownToString());
	}

	public static void grundyGame() {
		/* create a new game */
		System.out.println("\nGame\n");

		IGame firstState = new Grundy(7);
		TwoPlayerGame a = new TwoPlayerGame(firstState, null);
		TwoPlayerGame.addPlayer(Node.PLAYER_PC);
		TwoPlayerGame.addPlayer("Human");
		TwoPlayerGame.addObserver(new GameObserver());
		a.playAgainstPc("PC");
	}

	public static void grundyNewRule() {
		System.out.println("\nGame\n");
		IGame firstState = new GrundyNewRule(7);
		TwoPlayerGame a = new TwoPlayerGame(firstState, null);
		TwoPlayerGame.addPlayer(Node.PLAYER_PC);
		TwoPlayerGame.addPlayer("Human");
		TwoPlayerGame.addObserver(new GameObserver());
		a.playAgainstPc("PC");
	}
}

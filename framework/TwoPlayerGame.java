package framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import game.IGame;

/**
 *  class for two-player games
 */
public class TwoPlayerGame extends Node {

	private AlphaBetaWrapper alpha = new AlphaBetaWrapper();
	private AlphaBetaWrapper beta = new AlphaBetaWrapper();
	private int utilityValue = Integer.MIN_VALUE;

	public TwoPlayerGame(IGame gamestate) {
		this(gamestate, null);
	}

	public TwoPlayerGame(IGame gamestate, Node parent) {
		super(gamestate, parent);
	}

	public int alphaBetaSearch(boolean print) {
		player = players.get(0);
		int max = max(-Integer.MAX_VALUE, Integer.MAX_VALUE, true);
		return max;
	}

	public int play() {
		int max = max(-Integer.MAX_VALUE, Integer.MAX_VALUE, false);
		return max;
	}

	/* return utility value , if alphabeta is true do pruning */
	private int max(int alpha, int beta, boolean alphabeta) {
		int value;
		this.alpha.setValue(alpha);
		this.beta.setValue(beta);
		if (parent != null)
			player = changePlayer(parent.player);

		/* evaluation */
		if (iAmALeaf()) {
			utilityValue = gameState.evaluation();
			myUpdate(toBottomUpString2());
			return utilityValue;
		}
		for (IGame nextGS : gameState.possibleNextGameStates()) {
			/*
			 * avoid loops in game humans know when to quit loops but the
			 * computer takes that to the bitter not-end
			 */
			if (recursiveEquals(nextGS))
				continue;

			/* new move */
			TwoPlayerGame n = new TwoPlayerGame(nextGS, this);
			/* add move to game in memory */
			if (!n.unique())
				continue;

			/* recursively call max function */
			value = -n.max(-beta, -alpha, alphabeta);

			/* assign the max utilityvalue to this node */
			/*
			 * the "-" before the math is important, as well as the - before
			 * value
			 */
			utilityValue = -Math.max(-value, utilityValue);
			alpha = utilityValue;
			if (alphabeta) {
				this.alpha.setValue(alpha);
				this.beta.setValue(beta);
				if (value >= beta) {
					return beta;
				}
				if (value > alpha)
					alpha = value;
			}
		}
		return alpha;
	}

	public TwoPlayerGame move() {
		children.clear();
		alphaBetaSearch(false);
		TwoPlayerGame max = (TwoPlayerGame) children.get(0);
		TwoPlayerGame nn;
		for (Node n : children) {
			nn = (TwoPlayerGame) n;
			n.player = changePlayer("PC");
			myUpdate("Possible move\n " + nn.toString());
			if (max.utilityValue < nn.utilityValue)
				max = (TwoPlayerGame) n;
		}
		return max;
	}

	public TwoPlayerGame askNextMove() {
		int reponse = 0;
		myUpdate("Available moves");
		List<Node> next = possibleNextNodes();
		List<TwoPlayerGame> games = new ArrayList<TwoPlayerGame>();
		TwoPlayerGame tplg;
		for (int i = 0; i < next.size(); i++) {
			tplg = new TwoPlayerGame(next.get(i).gameState);
			tplg.player = changePlayer("Human");
			games.add(tplg);
			myUpdate("Move " + i + " " + tplg.player + " "
					+ (tplg.toMoveString()));
		}
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(isr);
			reponse = Integer.parseInt(in.readLine());
			if (reponse > next.size() - 1 || reponse < 0) {
				myUpdate("Give number between 0 and " + children.size()
						+ " excluded");
				return askNextMove();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return games.get(reponse);
	}

	public Node playAgainstPc(String firstPlayer) {
		TwoPlayerGame next = this;

		next.player = firstPlayer;
		player = firstPlayer;

		myUpdate(next.player + ": " + next.toMoveString() + "\n");

		while (!next.iAmALeaf()) {
			if (next.player == "PC") {
				next = next.move();
			} else {
				next = next.askNextMove();
			}
			myUpdate(next.player + ": " + next.toMoveString());
		}
		myUpdate(changePlayer(next.player) + " wins");
		return next;
	}

	/* some string methods two show game state */
	public String resultToString() {
		/* the first path we can not cut */
		for (int i = 2; i < children.size(); i++) {
			if (((TwoPlayerGame) children.get(i)).utilityValue
					* ((TwoPlayerGame) children.get(1)).utilityValue < 0)
				return "There is no player who always wins";
		}
		if (((TwoPlayerGame) children.get(0)).utilityValue > 0) {
			return "Player " + player + " always wins";
		} else
			return "Player " + ((TwoPlayerGame) children.get(0)).player
					+ " always wins";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(player + ": " + gameState.toString() + " ");
		sb.append("\n Alpha: " + alpha + "\n Beta: " + beta + "\n Value: "
				+ utilityValue + "\n\n");
		return sb.toString();
	}

	public String toMoveString() {
		StringBuilder sb = new StringBuilder();
		sb.append(gameState + " u: " + utilityValue);
		return sb.toString();
	}
}

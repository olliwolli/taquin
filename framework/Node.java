package framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import game.GameObserver;
import game.IGame;

/**
 * Represents a node in a game-tree.
 * 
 */

public class Node extends Observable implements Comparable<Node> {

	protected List<Node> children = new ArrayList<Node>();
	protected Node parent = null;

	private static Node firstLeafSeen = null;
	protected IGame gameState;

	private static List<GameObserver> observers = new ArrayList<GameObserver>();

	protected static final List<String> players = new ArrayList<String>();
	public static final String PLAYER_PC = "PC";
	protected String player;

	public Node(IGame gameState, Node parent) {
		this.gameState = gameState;
		this.parent = parent;
		if (unique() && parent != null)
			parent.children.add(this);
	}

	public boolean unique() {
		if (parent == null)
			return true;
		for (Node n : parent.children) {
			if (compareTo(n) == 0 && n != this)
				return false;
		}
		return true;
	}

	/*
	 * checks if the same game-state was seen before in this game
	 */
	public boolean recursiveEquals(IGame otherstate) {
		if (gameState.equals(otherstate))
			return true;
		else if (parent != null)
			return parent.recursiveEquals(otherstate);
		else
			return false;
	}

	/* tells if the current node is a leaf node */
	protected boolean iAmALeaf() {
		if (firstLeafSeen == null)
			firstLeafSeen = this;
		return gameState.gameOver();
	}

	public List<Node> possibleNextNodes() {
		List<Node> ns = new ArrayList<Node>();
		for (IGame n : gameState.possibleNextGameStates())
			ns.add(new Node(n, this));
		return ns;
	}

	public String toString() {
		return gameState.toString();
	}

	public int evaluation() {
		return gameState.evaluation();
	}

	public int cost(Node parent) {
		return gameState.cost(parent.gameState);
	}

	@Override
	public int compareTo(Node o) {
		return this.gameState.compareTo(o.gameState);
	}

	public int depth() {
		if (parent == null)
			return 0;
		return parent.depth() + 1;
	}

	public void toBottomUpPath(List<Node> list) {
		if (parent != null)
			parent.toBottomUpPath(list);
		list.add(this);
	}

	/* recursive toString() method to show the sequence of one game */
	public String toBottomUpString() {
		StringBuilder sb = new StringBuilder();
		if (parent != null)
			sb.append(parent.toBottomUpString());
		sb.append("\nNode " + depth() + "\n" + toString() + "\n");
		if (children.size() == 0) {
			if (firstLeafSeen == this)
				sb
						.append("\nFirst leaf seen, this situation may never be played.");
			// else{
			// sb.append("\nNo valid move possible, "+player+" looses");
			// }
		}
		return sb.toString();
	}

	public String toBottomUpString2() {
		StringBuilder sb = new StringBuilder();
		if (parent != null)
			sb.append(parent.toBottomUpString2());
		sb.append(gameState.toString());
		return sb.toString();
	}

	public String toTopDownToString() {
		StringBuilder sb = new StringBuilder();

		for (Node n : children) {
			sb.append(toString());
			sb.append(n.toTopDownToString());
		}

		if (children.size() == 0)
			return toString() + "\n";
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (gameState == null) {
			if (other.gameState != null)
				return false;
		} else if (!gameState.equals(other.gameState))
			return false;
		return true;
	}

	protected void myUpdate(String string) {
		for (GameObserver o : observers)
			o.update(this, string);
	}

	public static String changePlayer(String oldPlayer) {
		for (String p : players) {
			if (!p.equals(oldPlayer))
				return p;
		}
		return null;
	}

	public static void addPlayer(String player) {
		players.add(player);
	}

	public static synchronized void addObserver(GameObserver o) {
		observers.add(o);
	}

	public final IGame getGameState() {
		return gameState;
	}
}

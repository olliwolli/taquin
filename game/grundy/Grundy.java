package game.grundy;

import game.IGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Oliver-Tobias Ripka Class implementing the grundy game
 */
public class Grundy implements IGame {
	protected List<Integer> piles = new ArrayList<Integer>();

	public Grundy() {
	}

	public Object getState() {
		return piles;
	}

	public Grundy(int pile) {
		piles.add(pile);
	}

	public int cost(IGame a) {
		return -1;
	}

	/*
	 * plays one move and returns the new node associated with the move; returns
	 * null if there is no valid move (end of game)
	 */
	protected Grundy getGameState(Grundy parent, int selected, int combination) {
		int newpile1;
		int newpile2;

		/* add untouched piles to the new node */
		piles.addAll(parent.piles);
		piles.remove(parent.piles.get(selected));

		/* calculate a move */
		newpile1 = combination;
		newpile2 = parent.piles.get(selected) - combination;

		piles.add(newpile1);
		piles.add(newpile2);
		Collections.sort(piles);

		/* return this if the move is valid and wasn't calculated before */
		if (newpile1 != newpile2)
			return this;

		return null;
	}

	/* tells if the current node is a leaf node */
	public boolean gameOver() {
		if (piles == null)
			return false;
		for (int i : piles) {
			if (i > 2)
				return false;
		}
		return true;
	}

	/* utility evaluation function, piles that have two coins */
	public int evaluation() {
		int count = 0;
		for (Integer pile : piles) {
			if (pile == 2)
				count++;
		}
		return count;
	}

	@Override
	public int compareTo(IGame anotherGame) {
		Grundy n;
		if (!(anotherGame instanceof Grundy))
			return -1;
		n = (Grundy) anotherGame;

		for (int i = 0; i < piles.size(); i++) {
			if (n.piles.get(i) < piles.get(i))
				return 1;
			else if (n.piles.get(i) > piles.get(i))
				return -1;
		}
		return 0;
	}

	public String toString() {
		return piles.toString();
	}

	@Override
	public List<IGame> possibleNextGameStates() {

		List<IGame> possibleStates = new ArrayList<IGame>();
		for (int sel = 0; sel < piles.size(); sel++) {
			for (int comb = 1; comb < piles.get(sel); comb++) {
				Grundy n = new Grundy();

				/* skip invalid game moves */
				if (null == n.getGameState(this, sel, comb)) {
					continue;
				}
				if (!possibleStates.contains(n))
					possibleStates.add(n);
			}
		}
		return possibleStates;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grundy other = (Grundy) obj;
		if (piles == null) {
			if (other.piles != null)
				return false;
		} else if (!piles.toString().equals(other.piles.toString()))
			return false;
		return true;
	}

	/* not implemented */
	public IGame clone() {
		return this;
	}
}
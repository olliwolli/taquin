package game.grundy;

import game.IGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Oliver-Tobias Ripka Grundy game with anther rule. fusion of piles
 *         with equal height extends original game and overrides different
 *         behaviour
 */
public class GrundyNewRule extends Grundy {

	public GrundyNewRule(int pile) {
		super(pile);
	}

	public GrundyNewRule() {

	}

	public GrundyNewRule(List<Integer> piles) {
		for (Integer pile : piles)
			super.piles.add(pile);
	}

	/*
	 * evaluation function for new rule (coins can be unified when they have the
	 * same size)
	 * 
	 * I still use the old evaluation function first since it is still good to
	 * have staples of 2
	 * 
	 * Then I count the number of staples that one have one coin if the number
	 * of these is even this is good for the player if not it is bad Since the
	 * other player can avoid dividing staples which have two coins (game over
	 * condition "do not divide in equal staples") by merging staples with one
	 * coin. But this only works until one staple with one coin is left and all
	 * the other staples are of height 2. So if the number of one-coin-staples
	 * is bigger than one and the number of one-coin-staples divided by two is
	 * odd the current player can avoid the game over condition until to the
	 * end. In the other case the other player will be able to do that.
	 */
	public int evaluation() {
		int count = super.evaluation();

		int countone = 0;
		for (Integer pile : piles) {
			if (pile == 1)
				countone++;
		}
		/* TODO */
		if (countone > 1 && (countone / 2) % 2 == 1)
			countone++;
		return count;
	}

	protected Grundy getGameState(Grundy parent, int dupe) {
		/* add untouched piles to the new node */
		super.piles.addAll(parent.piles);
		int numremoved = 0;

		/* remove duplicate piles from list */
		while (numremoved < 2) {
			for (int i = 0; i < piles.size(); i++) {
				if (piles.get(i).equals(dupe)) {
					piles.remove(i);
					numremoved++;
				}
			}
		}

		/* add unified pile to list */
		piles.add(dupe * 2);

		return this;
	}

	public List<IGame> possibleNextGameStates() {
		/*
		 * get the possible states from the original game and change the type
		 */
		List<IGame> possibleStates = new ArrayList<IGame>();

		for (IGame state : super.possibleNextGameStates()) {
			GrundyNewRule newstate = new GrundyNewRule((List<Integer>) state
					.getState());
			possibleStates.add(newstate);
		}

		java.util.Collections.sort(piles);

		Set<Integer> dupes = new HashSet<Integer>();

		/* add piles of same size to set */
		for (int i = 0; i < piles.size() - 1; i++) {
			if (piles.get(i).equals(piles.get(i + 1)))
				dupes.add(piles.get(i));
		}

		for (Integer dupe : dupes) {
			GrundyNewRule n = new GrundyNewRule();
			/* skip invalid game moves */
			if (null == n.getGameState(this, dupe)) {
				continue;
			}
			if (!possibleStates.contains(n))
				possibleStates.add(n);
		}
		return possibleStates;
	}

	/* tells if the current node is a leaf node */
	public boolean gameOver() {
		int countones = 0;
		if (piles == null)
			return false;
		for (int i : piles) {
			if (i > 2)
				return false;
			if (i == 1)
				countones++;
		}

		if (countones != 1)
			return false;
		return true;
	}
}

package game;

import framework.Node;

/**
 * Simple game observer, just write the update to the console
 * 
 * @author Oliver-Tobias Ripka
 * 
 */
public class GameObserver {

	public void update(Node o, Object arg) {
		System.out.println(((String) arg));
	}
}

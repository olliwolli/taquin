package framework;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import game.IGame;

/* generic astar implementation */
public class AStar extends Node implements Runnable {
	public Node lastnode;

	@Override
	public void run() {

		lastnode = astar(null);
	}

	private int f = -1;
	private int g;
	private int h;
	private int c;
	private static final Queue<AStar> openQueue = new PriorityQueue<AStar>(500,
			new Comparator<AStar>() {
				public int compare(AStar n, AStar o) {
					if (n.f == o.f)
						return 0;
					return n.f < o.f ? -1 : +1;
				}
			});

	private static final Map<AStar, AStar> closedList = new TreeMap<AStar, AStar>();
	private static final Map<AStar, AStar> hashOpen = new TreeMap<AStar, AStar>();

	public static final int MAXITER = 100000;

	public AStar(IGame gamestate) {
		super(gamestate, null);
		hashOpen.clear();
		closedList.clear();
		openQueue.clear();
		openQueue.add(this);
	}

	public AStar(Node n) {
		super(n.gameState, n.parent);
	}

	public String toString() {
		getF();
		return "f=" + f + ", g=" + g + ", c=" + c + ", h=" + h + "\n"
				+ super.toString();
	}

	public Node astar(SwingWorker<String, Object> worker) {
		AStar currentNode;
		int i = 0;
		boolean canceled = false;
		while (openQueue.size() != 0 && i < MAXITER && !canceled) {
			if (worker != null)
				canceled = worker.isCancelled();

			if (i % 100 == 0) {
				setChanged();
				// System.out.println("Queue size: "+openQueue.size()+" Iterations: "+i);
				notifyObservers("Queue size: " + openQueue.size()
						+ " Iterations: " + i);
			}
			currentNode = openQueue.poll();
			closedList.put(currentNode, currentNode);

			if (currentNode.gameState.gameOver()) {

				System.out.println("Found: " + currentNode.toString());
				System.out.println("after " + i + " iterations");
				return currentNode;
			}
			// System.out.println("Current Node: "+currentNode.toString());
			expandNode(currentNode);

			i++;
		}
		System.out.println("Queue size: " + openQueue.size() + " Iterations: "
				+ i);
		notifyObservers("Queue size: " + openQueue.size() + " Iterations: " + i);
		return null;
	}

	/* node is parent */
	private int getF() {
		f = evaluation();

		if (parent != null) {
			h = f;
			c = 1;// cost(parent);
			g = parent.depth();
			f += c + g;
		}
		return f;
	}

	public void expandNode(AStar node) {
		AStar childInMap;
		for (Node child : node.possibleNextNodes()) {

			if (closedList.containsKey(child))
				continue;

			AStar achild = new AStar(child);
			// System.out.println("Next move: "+achild.toString());
			achild.getF();

			childInMap = hashOpen.get(child);

			if (childInMap != null && achild.f > childInMap.f) {
				// System.out.println("Not a good way: "+achild.toString()+"\n\n--------------------");
				continue;
			}
			achild.parent = node;

			if (childInMap != null) {
				// System.out.println("Found better way: "+achild.toBottomUpString()+"\n\n-------------------");
				removeNode(achild);
				addNode(achild);
			} else {
				addNode(achild);
			}
		}
	}

	private void addNode(AStar n) {
		openQueue.add(n);
		hashOpen.put(n, n);
	}

	private void removeNode(AStar n) {
		openQueue.remove(n);
		hashOpen.remove(n);
	}

	public int getG() {
		return depth();
	}
}

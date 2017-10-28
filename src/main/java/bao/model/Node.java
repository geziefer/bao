package bao.model;

public class Node {
	private int no;
	private int counter;
	private int simulateCounter;
	private Player player;
	private Node next;
	private Node previous;
	private Node opposite;

	public Node(int no, int counter, Player player, Node previous) {
		this.no = no;
		this.counter = counter;
		this.player = player;
		this.next = null;
		this.previous = previous;
		this.opposite = null;
	}

	public int provideCounter(Mode mode) {
		if (mode == Mode.PLAY) {
			return counter;
		} else {
			return simulateCounter;
		}
	}

	public void resetCounter(Mode mode) {
		if (mode == Mode.PLAY) {
			counter = 0;
		} else {
			simulateCounter = 0;
		}
	}

	public void increaseCounter(int count, Mode mode) {
		if (mode == Mode.PLAY) {
			counter += count;
		} else {
			simulateCounter += count;
		}
	}

	public void resetSimulation() {
		simulateCounter = counter;
	}

	public Player getPlayer() {
		return player;
	}

	public Node getNext() {
		return next;
	}

	protected void setNext(Node next) {
		this.next = next;
	}

	public Node getPrevious() {
		return previous;
	}

	protected void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getOpposite() {
		return opposite;
	}

	protected void setOpposite(Node opposite) {
		this.opposite = opposite;
	}

	public int getNo() {
		return no;
	}

}

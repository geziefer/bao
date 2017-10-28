package bao.model;

public class Node {
	private int no;
	private int counter;
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

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
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

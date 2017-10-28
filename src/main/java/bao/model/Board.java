package bao.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public final static int PLAYER_NODES = 16;
	public final static int NODE_STONES = 2;

	List<Node> sideA = new ArrayList<>(); // white
	List<Node> sideB = new ArrayList<>(); // black

	public Board() {
		Node currentNode;
		Node previousNode = null;
		Node oppositeNode;

		// add all nodes of 1st player, set previous from 2nd on
		for (int i = 0; i < PLAYER_NODES; i++) {
			currentNode = new Node(i, NODE_STONES, Player.WHITE, previousNode);
			sideA.add(currentNode);
			previousNode = currentNode;
		}

		// link last to 1st node and add next links for 1st player
		currentNode = sideA.get(0);
		currentNode.setNext(sideA.get(PLAYER_NODES - 1));
		while (currentNode.getNext() == null) {
			previousNode = currentNode.getPrevious();
			previousNode.setNext(currentNode);
			currentNode = previousNode;
		}

		// add all nodes of 2nd player, set previous from last of 1st player
		for (int i = 0; i < PLAYER_NODES; i++) {
			currentNode = new Node(i, NODE_STONES, Player.BLACK, previousNode);
			sideB.add(currentNode);
			previousNode = currentNode;
		}

		// link last to 1st node and add next links for 1st player
		currentNode = sideB.get(0);
		currentNode.setNext(sideB.get(PLAYER_NODES - 1));
		while (currentNode.getNext() == null) {
			previousNode = currentNode.getPrevious();
			previousNode.setNext(currentNode);
			currentNode = previousNode;
		}

		// link all opposite nodes in middle row for both players
		for (int i = PLAYER_NODES - 1; i >= PLAYER_NODES / 2; i--) {
			int j = PLAYER_NODES / 2;
			currentNode = sideA.get(i);
			oppositeNode = sideB.get(j);
			currentNode.setOpposite(oppositeNode);
			oppositeNode.setOpposite(currentNode);
			j++;
		}

	}

	public void move(Player player, Direction direction, int nodeNo) {
		List<Node> side = player == Player.WHITE ? sideA : sideB;
		Node currentNode = side.get(nodeNo);
		while (currentNode.getCounter() >= 2) {
		}
	}

	public void print() {
		// print side B numbers
		System.out.print("   ");
		for (int i = PLAYER_NODES / 2 - 1; i >= 0; i--) {
			System.out.format("%2d", i);
			System.out.print(i > 0 ? " " : "\n");
		}

		// print upper header
		System.out.print("  ┌");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "┬" : "┐\n");
		}

		// print lower side B descending
		System.out.print("  │");
		for (int i = PLAYER_NODES / 2 - 1; i >= 0; i--) {
			System.out.format("%2d", sideB.get(i).getCounter());
			System.out.print(i > 0 ? "│" : "│ A\n");
		}

		// print side B separator
		System.out.print("S ├");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "┼" : "┤\n");
		}

		// print upper side B descending
		System.out.print("  │");
		for (int i = PLAYER_NODES / 2; i < PLAYER_NODES; i++) {
			System.out.format("%2d", sideB.get(i).getCounter());
			System.out.print(i < PLAYER_NODES - 1 ? "│" : "│ B\n");
		}

		// print middle separator
		System.out.print("  ╞");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("══");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "╪" : "╡\n");
		}

		// print upper side A ascending
		System.out.print("  │");
		for (int i = PLAYER_NODES - 1; i >= PLAYER_NODES / 2; i--) {
			System.out.format("%2d", sideA.get(i).getCounter());
			System.out.print(i >= PLAYER_NODES / 2 + 1 ? "│" : "│ B\n");
		}

		// print side A separator
		System.out.print("W ├");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "┼" : "┤\n");
		}

		// print lower side A ascending
		System.out.print("  │");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.format("%2d", sideA.get(i).getCounter());
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "│" : "│ A\n");
		}

		// print lower header
		System.out.print("  └");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "┴" : "┘\n");
		}

		// print side A numbers
		System.out.print("   ");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.format("%2d", i);
			System.out.print(i < PLAYER_NODES / 2 - 1 ? " " : "\n");
		}
	}
}

package bao.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	public final static int PLAYER_NODES = 16;
	public final static int NODE_STONES = 2;

	private List<Node> sideA = new ArrayList<>(); // white
	private List<Node> sideB = new ArrayList<>(); // black
	private boolean printResult;

	public Board(boolean printResult) {
		this.printResult = printResult;

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
		currentNode.setPrevious(sideA.get(PLAYER_NODES - 1));
		previousNode = currentNode.getPrevious();
		while (previousNode.getNext() == null) {
			previousNode.setNext(currentNode);
			currentNode = previousNode;
			previousNode = currentNode.getPrevious();
		}

		// add all nodes of 2nd player, set previous from 2nd on
		previousNode = null;
		for (int i = 0; i < PLAYER_NODES; i++) {
			currentNode = new Node(i, NODE_STONES, Player.BLACK, previousNode);
			sideB.add(currentNode);
			previousNode = currentNode;
		}

		// link last to 1st node and add next links for 2nd player
		currentNode = sideB.get(0);
		currentNode.setPrevious(sideB.get(PLAYER_NODES - 1));
		previousNode = currentNode.getPrevious();
		while (currentNode.getNext() == null) {
			previousNode.setNext(currentNode);
			currentNode = previousNode;
			previousNode = currentNode.getPrevious();
		}

		// link all opposite nodes in middle row for both players
		int j = PLAYER_NODES / 2;
		for (int i = PLAYER_NODES - 1; i >= PLAYER_NODES / 2; i--) {
			currentNode = sideA.get(i);
			oppositeNode = sideB.get(j);
			currentNode.setOpposite(oppositeNode);
			oppositeNode.setOpposite(currentNode);
			j++;
		}

	}

	public int getCountA() {
		return sideA.stream().mapToInt(i -> i.getCounter()).sum();
	}

	public int getCountB() {
		return sideA.stream().mapToInt(i -> i.getCounter()).sum();
	}

	public void move(Player player, Row row, int nodeNo, Direction direction) {
		// TODO: validate input and check if move is allowed (special 1st move)
		// calculate start node from players input
		List<Node> side = player == Player.WHITE ? sideA : sideB;
		Node currentNode = side.get(row == Row.LOWER ? nodeNo : PLAYER_NODES - 1 - nodeNo);
		// continue as long as there are at least 2 stones at end of move
		while (currentNode.getCounter() >= 2) {
			// empty start node and move for stone number
			int moves = currentNode.getCounter();
			currentNode.resetCounter();
			// move in direction for stone count and leave 1 in each
			for (int i = 0; i < moves; i++) {
				currentNode = (direction == Direction.CLOCK ? currentNode.getPrevious() : currentNode.getNext());
				currentNode.increaseCounter(1);
			}
			// if there's an oponent node and continuing allowed, take them over
			Node oppositeNode = currentNode.getOpposite();
			if (oppositeNode != null && oppositeNode.getCounter() >= 1 && currentNode.getCounter() >= 2) {
				currentNode.increaseCounter(oppositeNode.getCounter());
				oppositeNode.resetCounter();
			}

			if (printResult) {
				print();
			}
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
		System.out.print("A │");
		for (int i = PLAYER_NODES / 2 - 1; i >= 0; i--) {
			System.out.format("%2d", sideB.get(i).getCounter());
			System.out.print(i > 0 ? "│" : "│\n");
		}

		// print side B separator
		System.out.print("  ├");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "┼" : "┤ S (" + getCountB() + ")\n");
		}

		// print upper side B descending
		System.out.print("B │");
		for (int i = PLAYER_NODES / 2; i < PLAYER_NODES; i++) {
			System.out.format("%2d", sideB.get(i).getCounter());
			System.out.print(i < PLAYER_NODES - 1 ? "│" : "│\n");
		}

		// print middle separator
		System.out.print("  ╞");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("══");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "╪" : "╡\n");
		}

		// print upper side A ascending
		System.out.print("B │");
		for (int i = PLAYER_NODES - 1; i >= PLAYER_NODES / 2; i--) {
			System.out.format("%2d", sideA.get(i).getCounter());
			System.out.print(i >= PLAYER_NODES / 2 + 1 ? "│" : "│\n");
		}

		// print side A separator
		System.out.print("  ├");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "┼" : "┤ W (" + getCountA() + ")\n");
		}

		// print lower side A ascending
		System.out.print("A │");
		for (int i = 0; i < PLAYER_NODES / 2; i++) {
			System.out.format("%2d", sideA.get(i).getCounter());
			System.out.print(i < PLAYER_NODES / 2 - 1 ? "│" : "│\n");
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

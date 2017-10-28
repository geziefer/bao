package bao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bao.view.ConsolePrinter;

public class Board {
	public final static int PLAYER_NODES = 16;
	public final static int NODE_STONES = 2;

	private HashMap<Player, List<Node>> sides;
	private boolean printResult;

	public Board(boolean printResult) {
		this.printResult = printResult;
		sides = new HashMap<>();
		ArrayList<Node> firstSide = new ArrayList<>();
		ArrayList<Node> secondSide = new ArrayList<>();
		sides.put(Player.WHITE, firstSide);
		sides.put(Player.BLACK, secondSide);

		Node currentNode;
		Node previousNode = null;
		Node oppositeNode;

		// add all nodes of 1st player, set previous from 2nd on
		for (int i = 0; i < PLAYER_NODES; i++) {
			currentNode = new Node(i, NODE_STONES, Player.WHITE, previousNode);
			firstSide.add(currentNode);
			previousNode = currentNode;
		}

		// link last to 1st node and add next links for 1st player
		currentNode = firstSide.get(0);
		currentNode.setPrevious(firstSide.get(PLAYER_NODES - 1));
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
			secondSide.add(currentNode);
			previousNode = currentNode;
		}

		// link last to 1st node and add next links for 2nd player
		currentNode = secondSide.get(0);
		currentNode.setPrevious(secondSide.get(PLAYER_NODES - 1));
		previousNode = currentNode.getPrevious();
		while (previousNode.getNext() == null) {
			previousNode.setNext(currentNode);
			currentNode = previousNode;
			previousNode = currentNode.getPrevious();
		}

		// link all opposite nodes in middle row for both players
		int j = PLAYER_NODES / 2;
		for (int i = PLAYER_NODES - 1; i >= PLAYER_NODES / 2; i--) {
			currentNode = firstSide.get(i);
			oppositeNode = secondSide.get(j);
			currentNode.setOpposite(oppositeNode);
			oppositeNode.setOpposite(currentNode);
			j++;
		}
	}

	public HashMap<Player, List<Node>> getSides() {
		return sides;
	}

	public int sumCounters(Player player, Mode mode) {
		// select side from map and sum up counters depending on mode
		List<Node> side = sides.get(player);
		if (mode == Mode.PLAY) {
			return side.stream().mapToInt(i -> i.getCounter()).sum();
		} else {
			return side.stream().mapToInt(i -> i.getSimulateCounter()).sum();
		}
	}

	public void resetSimulation(Player player) {
		// select side and reset all simulation counters
		List<Node> side = sides.get(player);
		side.stream().forEach(i -> i.resetSimulation());
	}

	public void move(Player player, Row row, int nodeNo, Direction direction, Mode mode) {
		// TODO: validate input and check if move is allowed (special 1st move)
		// calculate start node from players input
		List<Node> side = sides.get(player);
		Node currentNode = side.get(row == Row.LOWER ? nodeNo : PLAYER_NODES - 1 - nodeNo);
		// continue as long as there are at least 2 stones at end of move
		while (currentNode.getCounter() >= 2) {
			// empty start node and move for stone number
			int moves = currentNode.getCounter();
			currentNode.resetCounter(mode);
			// move in direction for stone count and leave 1 in each
			for (int i = 0; i < moves; i++) {
				currentNode = (direction == Direction.CLOCK ? currentNode.getPrevious() : currentNode.getNext());
				currentNode.increaseCounter(1, mode);
			}
			// if there's an oponent node and continuing allowed, take them over
			Node oppositeNode = currentNode.getOpposite();
			if (oppositeNode != null && oppositeNode.getCounter() >= 1 && currentNode.getCounter() >= 2) {
				currentNode.increaseCounter(oppositeNode.getCounter(), mode);
				oppositeNode.resetCounter(mode);
			}

			if (printResult && mode == Mode.PLAY) {
				ConsolePrinter.printBoard(this);
			}
		}
	}
}

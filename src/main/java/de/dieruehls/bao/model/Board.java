package de.dieruehls.bao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import de.dieruehls.bao.view.ConsolePrinter;

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
		sides.put(Player.WEISS, firstSide);
		sides.put(Player.SCHWARZ, secondSide);

		Node currentNode;
		Node previousNode = null;
		Node oppositeNode;

		// add all nodes of 1st player, set previous from 2nd on
		for (int i = 0; i < PLAYER_NODES; i++) {
			currentNode = new Node(i, NODE_STONES, Player.WEISS, previousNode);
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
			currentNode = new Node(i, NODE_STONES, Player.SCHWARZ, previousNode);
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
		return side.stream().mapToInt(n -> n.provideCounter(mode)).sum();
	}

	public void resetSimulation() {
		// reset all simulation counters for both players
		for (Player player : Player.values()) {
			List<Node> side = sides.get(player);
			side.stream().forEach(i -> i.resetSimulation());
		}
	}

	public boolean makeMove(Player player, Move move, Mode mode) {
		// calculate start node from players input
		List<Node> side = sides.get(player);
		Node currentNode = side
				.get(move.getRow() == Row.LOWER ? move.getNodeNo() : PLAYER_NODES - 1 - move.getNodeNo());

		// validate move if possible
		if (currentNode.provideCounter(mode) < 2) {
			return false;
		}

		// continue as long as there are at least 2 stones at end of move
		while (currentNode.provideCounter(mode) >= 2) {
			// empty start node and move for stone number
			int moves = currentNode.provideCounter(mode);
			currentNode.resetCounter(mode);
			// move in direction for stone count and leave 1 in each
			for (int i = 0; i < moves; i++) {
				currentNode = (move.getDirection() == Direction.CLOCK ? currentNode.getPrevious()
						: currentNode.getNext());
				currentNode.increaseCounter(1, mode);
			}
			// if there's an oponent node and continuing allowed, take them over
			Node oppositeNode = currentNode.getOpposite();
			if (oppositeNode != null && oppositeNode.provideCounter(mode) >= 1
					&& currentNode.provideCounter(mode) >= 2) {
				currentNode.increaseCounter(oppositeNode.provideCounter(mode), mode);
				oppositeNode.resetCounter(mode);
			}

			if (printResult && mode == Mode.PLAY) {
				ConsolePrinter.printBoard(this);
			}
		}

		return true;
	}

	public Move calculateBestMove(Player player) {
		int maxCounter = 0;
		Move bestMove = null;

		// select player side and check for each node
		List<Node> side = sides.get(player);
		for (Node node : side) {
			// construct move from node settings
			Row row = node.getNo() < PLAYER_NODES / 2 ? Row.LOWER : Row.UPPER;
			int nodeNo = row == Row.LOWER ? node.getNo() : PLAYER_NODES - node.getNo() - 1;
			// for each direction simulate move
			for (Direction direction : Direction.values()) {
				Move move = new Move(row, nodeNo, direction);
				resetSimulation();
				boolean possible = makeMove(player, move, Mode.SIMULATE);
				if (possible) {
					int currentCounter = sumCounters(player, Mode.SIMULATE);
					// currently best move found
					if (currentCounter > maxCounter) {
						maxCounter = currentCounter;
						bestMove = move;
					}
				}
			}
		}

		return bestMove;
	}

	public Player checkLoser() {
		// lose conditions are either all upper nodes are empty or no node
		// contains movable stones
		Predicate<Node> isUpper = n -> n.getOpposite() != null;
		Predicate<Node> isEmpty = n -> n.provideCounter(Mode.PLAY) == 0;
		Predicate<Node> isNotMoveable = n -> n.provideCounter(Mode.PLAY) <= 1;

		// check both conditions for each side
		for (Player player : Player.values()) {
			if (sides.get(player).stream().filter(isUpper).allMatch(isEmpty)
					|| sides.get(player).stream().allMatch(isNotMoveable)) {
				return player;
			}
		}

		// null means no loser yet
		return null;
	}
}

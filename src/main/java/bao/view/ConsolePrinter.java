package bao.view;

import java.util.List;

import bao.model.Board;
import bao.model.Mode;
import bao.model.Node;
import bao.model.Player;

public class ConsolePrinter {
	public static void printBoard(Board board) {
		// print side B numbers
		List<Node> side = board.getSides().get(Player.BLACK);
		System.out.print("   ");
		for (int i = Board.PLAYER_NODES / 2 - 1; i >= 0; i--) {
			System.out.format("%2d", i);
			System.out.print(i > 0 ? " " : "\n");
		}

		// print upper header
		System.out.print("  ┌");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < Board.PLAYER_NODES / 2 - 1 ? "┬" : "┐\n");
		}

		// print lower side B descending
		System.out.print("A │");
		for (int i = Board.PLAYER_NODES / 2 - 1; i >= 0; i--) {
			System.out.format("%2d", side.get(i).getCounter());
			System.out.print(i > 0 ? "│" : "│\n");
		}

		// print side B separator
		System.out.print("  ├");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < Board.PLAYER_NODES / 2 - 1 ? "┼"
					: "┤ S (" + board.sumCounters(Player.BLACK, Mode.PLAY) + ")\n");
		}

		// print upper side B descending
		System.out.print("B │");
		for (int i = Board.PLAYER_NODES / 2; i < Board.PLAYER_NODES; i++) {
			System.out.format("%2d", side.get(i).getCounter());
			System.out.print(i < Board.PLAYER_NODES - 1 ? "│" : "│\n");
		}

		// print middle separator
		System.out.print("  ╞");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			System.out.print("══");
			System.out.print(i < Board.PLAYER_NODES / 2 - 1 ? "╪" : "╡\n");
		}

		// print upper side A ascending
		side = board.getSides().get(Player.WHITE);
		System.out.print("B │");
		for (int i = Board.PLAYER_NODES - 1; i >= Board.PLAYER_NODES / 2; i--) {
			System.out.format("%2d", side.get(i).getCounter());
			System.out.print(i >= Board.PLAYER_NODES / 2 + 1 ? "│" : "│\n");
		}

		// print side A separator
		System.out.print("  ├");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < Board.PLAYER_NODES / 2 - 1 ? "┼"
					: "┤ W (" + board.sumCounters(Player.WHITE, Mode.PLAY) + ")\n");
		}

		// print lower side A ascending
		System.out.print("A │");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			System.out.format("%2d", side.get(i).getCounter());
			System.out.print(i < Board.PLAYER_NODES / 2 - 1 ? "│" : "│\n");
		}

		// print lower header
		System.out.print("  └");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			System.out.print("──");
			System.out.print(i < Board.PLAYER_NODES / 2 - 1 ? "┴" : "┘\n");
		}

		// print side A numbers
		System.out.print("   ");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			System.out.format("%2d", i);
			System.out.print(i < Board.PLAYER_NODES / 2 - 1 ? " " : "\n");
		}
	}
}

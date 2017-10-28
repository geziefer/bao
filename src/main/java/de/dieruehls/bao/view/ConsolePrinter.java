package de.dieruehls.bao.view;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import de.dieruehls.bao.model.Board;
import de.dieruehls.bao.model.Mode;
import de.dieruehls.bao.model.Node;
import de.dieruehls.bao.model.Player;

public class ConsolePrinter {
	public static void printBoard(Board board) {
		PrintStream out = null;
		try {
			out = new PrintStream(System.out, true, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// print side B numbers
		List<Node> side = board.getSides().get(Player.SCHWARZ);
		out.print("   ");
		for (int i = Board.PLAYER_NODES / 2 - 1; i >= 0; i--) {
			out.format("%2d", i);
			out.print(i > 0 ? " " : "\n");
		}

		// print upper header
		out.print("  \u250f");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			out.print("\u2501\u2501");
			out.print(i < Board.PLAYER_NODES / 2 - 1 ? "\u252f" : "\u2513\n");
		}

		// print lower side B descending
		out.print("A \u2503");
		for (int i = Board.PLAYER_NODES / 2 - 1; i >= 0; i--) {
			out.format("%2d", side.get(i).provideCounter(Mode.PLAY));
			out.print(i > 0 ? "\u2502" : "\u2503\n");
		}

		// print side B separator
		out.print("  \u2520");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			out.print("\u2500\u2500");
			out.print(i < Board.PLAYER_NODES / 2 - 1 ? "\u253c"
					: "\u2528 S (" + board.sumCounters(Player.SCHWARZ, Mode.PLAY) + ")\n");
		}

		// print upper side B descending
		out.print("B \u2503");
		for (int i = Board.PLAYER_NODES / 2; i < Board.PLAYER_NODES; i++) {
			out.format("%2d", side.get(i).provideCounter(Mode.PLAY));
			out.print(i < Board.PLAYER_NODES - 1 ? "\u2502" : "\u2503\n");
		}

		// print middle separator
		out.print("  \u2523");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			out.print("\u2501\u2501");
			out.print(i < Board.PLAYER_NODES / 2 - 1 ? "\u253f" : "\u252b\n");
		}

		// print upper side A ascending
		side = board.getSides().get(Player.WEISS);
		out.print("B \u2503");
		for (int i = Board.PLAYER_NODES - 1; i >= Board.PLAYER_NODES / 2; i--) {
			out.format("%2d", side.get(i).provideCounter(Mode.PLAY));
			out.print(i >= Board.PLAYER_NODES / 2 + 1 ? "\u2502" : "\u2503\n");
		}

		// print side A separator
		out.print("  \u2520");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			out.print("\u2500\u2500");
			out.print(i < Board.PLAYER_NODES / 2 - 1 ? "\u253c"
					: "\u2528 W (" + board.sumCounters(Player.WEISS, Mode.PLAY) + ")\n");
		}

		// print lower side A ascending
		out.print("A \u2503");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			out.format("%2d", side.get(i).provideCounter(Mode.PLAY));
			out.print(i < Board.PLAYER_NODES / 2 - 1 ? "\u2502" : "\u2503\n");
		}

		// print lower header
		out.print("  \u2517");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			out.print("\u2501\u2501");
			out.print(i < Board.PLAYER_NODES / 2 - 1 ? "\u2537" : "\u251b\n");
		}

		// print side A numbers
		out.print("   ");
		for (int i = 0; i < Board.PLAYER_NODES / 2; i++) {
			out.format("%2d", i);
			out.print(i < Board.PLAYER_NODES / 2 - 1 ? " " : "\n");
		}
	}
}

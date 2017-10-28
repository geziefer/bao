package bao;

import java.util.Scanner;

import bao.model.Board;
import bao.model.Direction;
import bao.model.Mode;
import bao.model.Player;
import bao.model.Row;
import bao.view.ConsolePrinter;

public class Game {
	public static boolean SHOW_INTERMEDIATE_RESULTS = false;

	Board board;

	public static void main(String[] args) {
		new Game().play();
	}

	public Game() {
		board = new Board(SHOW_INTERMEDIATE_RESULTS);
	}

	private void play() {
		ConsolePrinter.printBoard(board);

		Scanner reader = new Scanner(System.in);
		String input = "";
		while (!input.equals("X")) {
			System.out.print("Dein Zug: ");
			input = reader.nextLine();
			if (input.matches("[ab][0-7][mg]")) {
				board.move(Player.WHITE, input.charAt(0) == 'a' ? Row.LOWER : Row.UPPER,
						Character.getNumericValue(input.charAt(1)),
						input.charAt(2) == 'm' ? Direction.CLOCK : Direction.COUNTERCLOCK, Mode.PLAY);
				if (!SHOW_INTERMEDIATE_RESULTS) {
					ConsolePrinter.printBoard(board);
				}
			}
		}
		reader.close();
	}

}

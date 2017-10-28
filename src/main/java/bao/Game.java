package bao;

import java.util.Scanner;

import bao.model.Board;
import bao.model.Direction;
import bao.model.Mode;
import bao.model.Move;
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

		// endless game loop, exit request or game end break
		while (true) {
			// get user input
			System.out.print("Dein Zug: ");
			String input = reader.nextLine();

			// x input requests exit
			if (input.equals("x")) {
				break;
			}

			// match for correct input:
			// <a or b row>
			// <0 to 7 column>
			// <m for clockwise or g for counterclockwise>
			if (input.matches("[ab][0-7][mg]")) {
				// execute user move
				Move move = new Move(input.charAt(0) == 'a' ? Row.LOWER : Row.UPPER,
						Character.getNumericValue(input.charAt(1)),
						input.charAt(2) == 'm' ? Direction.CLOCK : Direction.COUNTERCLOCK);
				boolean possible = board.makeMove(Player.WEISS, move, Mode.PLAY);
				if (possible) {
					if (!SHOW_INTERMEDIATE_RESULTS) {
						ConsolePrinter.printBoard(board);
					}

					// check for game end
					if (checkLoser()) {
						break;
					}

					// execute computer move
					System.out.println("<Enter drücken>");
					reader.nextLine();
					move = board.calculateBestMove(Player.SCHWARZ);
					System.out.println("Computer zieht: " + (move.getRow() == Row.LOWER ? 'a' : 'b') + move.getNodeNo()
							+ (move.getDirection() == Direction.CLOCK ? 'm' : 'g'));
					board.makeMove(Player.SCHWARZ, move, Mode.PLAY);
					if (!SHOW_INTERMEDIATE_RESULTS) {
						ConsolePrinter.printBoard(board);
					}

					// check for game end
					if (checkLoser()) {
						break;
					}
				}
			}
		}
		reader.close();
	}

	private boolean checkLoser() {
		Player player = board.checkLoser();
		if (player != null) {
			System.out.println("Spieler " + player + " hat verloren");
			return true;
		}
		return false;
	}

}

package bao;

import bao.model.Board;

public class Game {

	Board board;

	public static void main(String[] args) {
		new Game().play();
	}

	public Game() {
		board = new Board();
	}

	private void play() {
		board.print();
	}

}

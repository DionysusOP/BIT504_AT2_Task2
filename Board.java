import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel {
	public static final int GRID_WIDTH = 8;
	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

	Cell[][] cells;

	public Board() {
		cells = new Cell[GameMain.ROWS][GameMain.COLS];

		// Create a grid of cells for the game board
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	public boolean isDraw() {
		// Check if the game is a draw (no more empty cells)
		for (int row = 0; row < GameMain.ROWS; row++) {
			for (int col = 0; col < GameMain.COLS; col++) {
				if (cells[row][col].content == Player.Empty) {
					// There's at least one empty cell, so it's not a draw
					return false;
				}
			}
		}

		// All cells are filled, and no player has won, so it's a draw
		return true;
	}

	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
		// Check if a player has won after making their move

		// Check horizontally
		if (cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer
				&& cells[playerRow][2].content == thePlayer)
			return true;

		// Check vertically
		if (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer
				&& cells[2][playerCol].content == thePlayer)
			return true;

		// Check diagonally (from top-left to bottom-right)
		if (playerRow == playerCol && cells[0][0].content == thePlayer && cells[1][1].content == thePlayer
				&& cells[2][2].content == thePlayer)
			return true;

		// Check diagonally (from top-right to bottom-left)
		if (playerRow + playerCol == 2 && cells[0][2].content == thePlayer && cells[1][1].content == thePlayer
				&& cells[2][0].content == thePlayer)
			return true;

		// No winner, keep playing
		return false;
	}

	public void paint(Graphics g) {
		// Draw the cells
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col].paint(g);
			}
		}

		// Draw the grid lines
		g.setColor(Color.gray);
		for (int row = 1; row < GameMain.ROWS; ++row) {
			g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF, GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
					GRID_WIDTH, GRID_WIDTH);
		}
		for (int col = 1; col < GameMain.COLS; ++col) {
			g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
					GRID_WIDTH, GRID_WIDTH);
		}
	}

	public void setCellContent(int row, int col, Player player) {
		// Set the content of a cell to a player's symbol
		if (row >= 0 && row < GameMain.ROWS && col >= 0 && col < GameMain.COLS) {
			cells[row][col].content = player;
		}
	}
}

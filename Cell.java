import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {
	Player content; // Content of this cell (empty, cross, nought)
	int row, col; // Row and column of this cell

	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		// Initialize content to Empty
		content = Player.Empty;
	}

	public void paint(Graphics g) {
		// Prepare to draw symbols
		Graphics2D graphic2D = (Graphics2D) g;
		// Set symbol stroke size
		graphic2D.setStroke(
				new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		// Draw the symbol in the cell
		int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
		int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;

		// If the content is Cross
		if (content == Player.Cross) {
			graphic2D.setColor(Color.RED);
			int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
			int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
			// Draw a red cross
			graphic2D.drawLine(x1, y1, x2, y2);
			graphic2D.drawLine(x2, y1, x1, y2);
		}
		// If the content is Nought
		else if (content == Player.Nought) {
			graphic2D.setColor(Color.BLUE);
			// Draw a blue circle (nought)
			graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
		}
	}

	public void clear() {
		// Clear the cell's content by setting it to Empty
		content = Player.Empty;
	}

	public void setContent(Player player) {
		// Set the content of this cell to the specified player (Cross or Nought)
		content = player;
	}
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameMain extends JPanel implements MouseListener {
	public static final int ROWS = 3, COLS = 3; // Number of rows and columns on the game board.
	public static final String TITLE = "Tic Tac Toe"; // The title of the game window.

	public static final int CELL_SIZE = 100; // The size of each cell on the board.
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Total width of the game board.
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS; // Total height of the game board.
	public static final int CELL_PADDING = CELL_SIZE / 6; // Padding around symbols in each cell.
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // Size of X and O symbols.
	public static final int SYMBOL_STROKE_WIDTH = 8; // Thickness of X and O symbols.

	// These are variables that keep track of the game's state.
	private Board board; // The game board.
	private GameState currentState; // The current state of the game (playing, draw, X won, O won).
	private Player currentPlayer; // The player who is currently taking their turn (X or O).
	private JLabel statusBar; // A message shown at the bottom of the game window.

	public enum GameState {
		Draw, Cross_won, Nought_won, Playing; // Possible game states: draw, X won, O won, playing.
	}

	public GameMain() {
		// Add a mouse listener to this panel to handle user clicks.
		addMouseListener(this);

		// Create a status bar at the bottom of the game window.
		statusBar = new JLabel("         ");
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		statusBar.setOpaque(true);
		statusBar.setBackground(Color.LIGHT_GRAY);

		// Set up the layout of the panel.
		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

		// Create the game board and add it to this panel.
		board = new Board();
		add(board);

		// Initialize the game board and start the game.
		initGame();
	}

	public static void main(String[] args) {
		// Run the game in a window.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(TITLE);

				GameMain gameMain = new GameMain();
				frame.add(gameMain);

				// Make sure the window closes gracefully when the user tries to close it.
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						confirmExit(frame);
					}
				});

				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);

		// Ask the game board to draw itself.
		board.paint(g);

		// Set the message displayed in the status bar at the bottom.
		if (currentState == GameState.Playing) {
			statusBar.setForeground(Color.BLACK);
			if (currentPlayer == Player.Cross) {
				statusBar.setText("Player X's Turn");
			} else {
				statusBar.setText("Player O's Turn");
			}
		} else if (currentState == GameState.Draw) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("It's a Draw! Click to play again.");
		} else if (currentState == GameState.Cross_won) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("Player X Won! Click to play again.");
		} else if (currentState == GameState.Nought_won) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("Player O Won! Click to play again.");
		}
	}

	public void initGame() {
		// Initialize the game board by setting all cells to be empty.
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				board.cells[row][col].content = Player.Empty;
			}
		}
		currentState = GameState.Playing; // Set the initial game state to "playing."
		currentPlayer = Player.Cross; // Set the first player to be X.
	}

	public void updateGame(Player thePlayer, int row, int col) {
		// Check if the current player has won after their move.
		if (board.hasWon(thePlayer, row, col)) {
			// Set the game state to "draw" if there is no winner.
		} else if (board.isDraw()) {

		}

	}

	public void mouseClicked(MouseEvent e) {
		// Get the coordinates of the mouse click.
		int mouseX = e.getX();
		int mouseY = e.getY();
		int rowSelected = mouseY / CELL_SIZE; // Calculate the row clicked.
		int colSelected = mouseX / CELL_SIZE; // Calculate the column clicked.

		// Check if it's a valid move and the cell is empty.
		if (currentState == GameState.Playing) {
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
					&& board.cells[rowSelected][colSelected].content == Player.Empty) {
				// Update the game board with the current player's move.
				board.setCellContent(rowSelected, colSelected, currentPlayer);

				// Check for a win or a draw.
				if (board.hasWon(currentPlayer, rowSelected, colSelected)) {
					// Determine the winning player and set the game state accordingly.
					if (currentPlayer == Player.Cross) {
						currentState = GameState.Cross_won;
					} else {
						currentState = GameState.Nought_won;
					}
				} else if (board.isDraw()) {
					currentState = GameState.Draw;
				} else {
					// Switch to the other player's turn.
					currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
				}

				// Repaint the game board.
				repaint();
			} else {
				// The selected cell is already occupied; display a message.
				statusBar.setText("Choose an empty cell!");
			}
		} else {
			// The game is over; restart it.
			initGame();
			repaint();
		}
	}

	// Display a confirmation dialog before exiting the game.
	private static void confirmExit(JFrame frame) {
		int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			// User clicked Yes, so exit the application.
			System.exit(0);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

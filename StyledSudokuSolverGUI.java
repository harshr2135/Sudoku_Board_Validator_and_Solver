import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class StyledSudokuSolverGUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private RoundedButton validateButton = new RoundedButton("Validate");
    private RoundedButton solveButton = new RoundedButton("Solve");
    private RoundedButton clearButton = new RoundedButton("Clear Board");

    public StyledSudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#FAF6E3")); // Set background color

        // Add heading
        JLabel heading = new JLabel("Sudoku Solver", JLabel.CENTER);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 34));
        heading.setForeground(Color.decode("#091057"));
        add(heading, BorderLayout.NORTH);

        // Create grid panel with custom thick lines
        GridPanel boardPanel = new GridPanel();
        boardPanel.setLayout(new GridLayout(9, 9));

        // Initialize the grid cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("Calibri", Font.BOLD, 26));
                cells[i][j].setForeground(Color.decode("#091057"));
                cells[i][j].setBackground(Color.decode("#DBD3D3"));
                cells[i][j].setBorder(new LineBorder(Color.decode("#FAF6E3"))); // Light border
                boardPanel.add(cells[i][j]);
            }
        }

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#EC8305"));

        // Style buttons
        JButton[] buttons = {validateButton, solveButton, clearButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Calibri", Font.BOLD, 18));
            button.setBackground(Color.decode("#DBD3D3"));
            button.setForeground(Color.decode("#091057"));
        }

        // Add buttons to the button panel
        buttonPanel.add(validateButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        // Add panels to the frame
        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button functionalities
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = getBoard();
                if (SudokuSolver.isValid(board)) {
                    JOptionPane.showMessageDialog(null, "The board is valid.");
                } else {
                    JOptionPane.showMessageDialog(null, "The board is not valid.");
                }
            }
        });

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = getBoard();
                if (SudokuSolver.isValid(board) && SudokuSolver.solver(board)) {
                    setBoard(board);
                    JOptionPane.showMessageDialog(null, "Sudoku solved!");
                } else {
                    JOptionPane.showMessageDialog(null, "No solution exists or board is invalid.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
            }
        });
    }

    // Method to retrieve the current board configuration from the GUI
    private int[][] getBoard() {
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText();
                if (!text.isEmpty()) {
                    board[i][j] = Integer.parseInt(text);
                } else {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }

    // Method to set the board configuration in the GUI
    private void setBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText(board[i][j] == 0 ? "" : String.valueOf(board[i][j]));
            }
        }
    }

    // Method to clear the board
    private void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
            }
        }
    }

    // Custom panel for drawing thick grid lines
    private class GridPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.decode("#2A3663"));
            g2d.setStroke(new BasicStroke(6)); // Thick lines

            // Draw thick outer border
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

            
        }
    }

    // Custom button with rounded corners
    private class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
            super.paintComponent(g);
        }

        @Override
        public void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(getForeground());
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StyledSudokuSolverGUI gui = new StyledSudokuSolverGUI();
            gui.setVisible(true);
        });
    }
}


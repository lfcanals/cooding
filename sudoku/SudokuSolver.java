package sudoku;
/**
 * Backtracking to solve Sudokus.
 */
public class SudokuSolver {
    public static void main(String[] args) {
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        if (solveSudoku(puzzle)) {
            printPuzzle(puzzle);
        } else {
            System.out.println("No solution exists");
        }
    }

    public static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= board[0].length; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true; // Puzzle is solved
    }

    public static boolean isValid(int[][] board, int row, int col, int num) {
        // Check if num is not in the current row, column, and 3x3 subgrid
        for (int x = 0; x < board[0].length; x++) {
            if (board[row][x] == num || board[x][col] == num || 
                board[row - row % 3 + x / 3][col - col % 3 + x % 3] == num) {
                return false;
            }
        }
        return true;
    }

    public static void printPuzzle(int[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int d = 0; d < board[0].length; d++) {
                System.out.print(board[r][d]);
                System.out.print(" ");
            }
            System.out.print("\n");
            if ((r + 1) % 3 == 0) {
                System.out.print("");
            }
        }
    }
}


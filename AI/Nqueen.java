import java.util.*;

//constraint satisfaction problem
public class Nqueen {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose board size:");

        int n = sc.nextInt();
        int[][] board = new int[n][n];

        if (solveN(board, 0)) {
            System.out.println("Solution:");
            printBoard(board);
        } else {
            System.out.println("No solution Found");
        }

        sc.close();

    }

    // Recursive function to solve N-Queen problem
    public static boolean solveN(int[][] board, int col) {
        // Base case: If all queens are placed
        if (col >= board.length) {
            return true;
        }
        // Try placing queen in each row of current col
        for (int i = 0; i < board.length; i++) {

            // Check if safe to place queen here
            if (isSafe(board, i, col)) {
                board[i][col] = 1;

                // Recursively check if can place further queens
                if (solveN(board, col + 1)) {
                    return true;
                }

                board[i][col] = 0;
            }
        }

        return false;
    }

    public static boolean isSafe(int[][] board, int row, int col) {

        // check row and col
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == 1 || board[i][col] == 1) {
                return false;
            }
        }

        // check diagonally one side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // check diagonally other side
        for (int i = row, j = col; i < board.length && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
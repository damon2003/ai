import java.util.Scanner;

// Driver Class
public class AiTicTacToe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();

        TicTacToeAI game = new TicTacToeAI(playerName);
        game.displayPlayerName();
        game.playGame();
        scanner.close();
    }
}

// Game Class
class TicTacToeAI {
    private char[][] gameBoard; // board
    private String playerName;

    // constructor setting all neccesary data
    public TicTacToeAI(String playerName) {
        gameBoard = new char[3][3];
        this.playerName = playerName;
        initializeGameBoard();
    }

    // empty game board
    private void initializeGameBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col] = '-';
            }
        }
    }

    // getter method for player's name
    public String getPlayerName() {
        return playerName;
    }

    // Displaying name
    public void displayPlayerName() {
        System.out.println("Player's Name: " + playerName);
    }

    // checking for draw
    private boolean isGameBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // checking win position
    private boolean checkWin(char player) {
        for (int row = 0; row < 3; row++) {
            if (gameBoard[row][0] == player && gameBoard[row][1] == player && gameBoard[row][2] == player) {
                return true;
            }
        }
        for (int col = 0; col < 3; col++) {
            if (gameBoard[0][col] == player && gameBoard[1][col] == player && gameBoard[2][col] == player) {
                return true;
            }
        }
        if (gameBoard[0][0] == player && gameBoard[1][1] == player && gameBoard[2][2] == player) {
            return true;
        }
        if (gameBoard[0][2] == player && gameBoard[1][1] == player && gameBoard[2][0] == player) {
            return true;
        }
        return false;
    }

    // minimax value
    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin('X')) {
            return 10 - depth; // Return Positive Score
        } else if (checkWin('O')) {
            return depth - 10; // Return Negative Score
        } else if (isGameBoardFull()) {
            return 0; // Tie
        }

        int bestScore;
        if (isMaximizing) { // ai turn
            bestScore = Integer.MIN_VALUE; // lowest

            // check for first empty slot
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (gameBoard[row][col] == '-') {
                        gameBoard[row][col] = 'X';
                        int score = minimax(depth + 1, false); // call for User's turn
                        gameBoard[row][col] = '-';
                        // set bestScore to maximum
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else { // user's turn
            bestScore = Integer.MAX_VALUE; // highest
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (gameBoard[row][col] == '-') {
                        gameBoard[row][col] = 'O';
                        int score = minimax(depth + 1, true); // call for AI's turn
                        gameBoard[row][col] = '-';
                        // set bestScore to minimum
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        // if (bestScore == Integer.MIN_VALUE || bestScore == Integer.MAX_VALUE) {
        // return 0;
        // }
        return bestScore;
    }

    // make move via best score calling minimax function
    private void makeMove() {
        // Default Values
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == '-') {
                    gameBoard[row][col] = 'X';
                    int score = minimax(0, false);// depth = recursion depth, isMaximizing = AI's turn
                    gameBoard[row][col] = '-';

                    if (score > bestScore) {
                        // set temporary score (Maximum), row and col
                        bestScore = score;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }
        // Set "X" to optimal choice
        gameBoard[bestRow][bestCol] = 'X';
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe - You are 'O', and I am 'X'");
        System.out.println("Here's the initial empty board:");
        displayBoard();

        while (true) {
            if (checkWin('X')) {
                System.out.println("AI won. Better luck next time!");
                break;
            } else if (checkWin('O')) {
                System.out.println("Congratulations, " + getPlayerName() + "! You won!");
                break;
            } else if (isGameBoardFull()) {
                System.out.println("It's a draw! Good game!");
                break;
            }

            // 2D Input for 2D Array for User's move
            System.out.println("Your turn (row [0-2] and column [0-2]):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // Check if input is valid i.e. in boundaries + empty
            if (row < 0 || row > 2 || col < 0 || col > 2 || gameBoard[row][col] != '-') {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            gameBoard[row][col] = 'O';
            displayBoard();

            if (!checkWin('O') && !isGameBoardFull()) {
                makeMove(); // AI's move
                System.out.println("AI's move:");
                displayBoard();
                // int aiScore = evaluateScore();
                // System.out.println("AI's Heuristic Score: " + aiScore);
            }
        }
    }

    private void displayBoard() {
        System.out.println("-------------");
        for (int row = 0; row < 3; row++) {
            System.out.print("| ");
            for (int col = 0; col < 3; col++) {
                System.out.print(gameBoard[row][col] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
}

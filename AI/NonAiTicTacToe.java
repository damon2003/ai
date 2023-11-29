import java.util.Scanner;

public class NonAiTicTacToe {

    private char[][] ticTacToeBoard;
    private char humanSymbol = 'X';
    private char systemSymbol = 'O';

    public NonAiTicTacToe() {
        ticTacToeBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ticTacToeBoard[i][j] = ' ';
            }
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ticTacToeBoard[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    void printBoard() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(ticTacToeBoard[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    public void userChance() {
        Scanner sc = new Scanner(System.in);
        int x, y;
        do {
            System.out.println("Enter X coordinate (0,1,2):");
            x = sc.nextInt();
            System.out.println("Enter Y coordinate (0,1,2):");
            y = sc.nextInt();

            if (ticTacToeBoard[x][y] != ' ') {
                System.out.println("Invalid move! Cell already filled.");
            } else {
                ticTacToeBoard[x][y] = humanSymbol;
                break;
            }

        } while (true);
    }

    public void computerChance() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ticTacToeBoard[i][j] == ' ') {
                    ticTacToeBoard[i][j] = systemSymbol;
                    if (checkWin(systemSymbol)) {
                        return;
                    } else {
                        ticTacToeBoard[i][j] = ' ';
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ticTacToeBoard[i][j] == ' ') {
                    ticTacToeBoard[i][j] = humanSymbol;
                    if (checkWin(humanSymbol)) {
                        ticTacToeBoard[i][j] = systemSymbol;
                        return;
                    } else {
                        ticTacToeBoard[i][j] = ' ';
                    }
                }
            }
        }

        while (true) {
            int x = (int) (Math.random() * 3);
            int y = (int) (Math.random() * 3);
            if (ticTacToeBoard[x][y] == ' ') {
                ticTacToeBoard[x][y] = systemSymbol;
                break;
            }
        }
    }

    public boolean checkWin(char symbol) {
        for (int i = 0; i < 3; i++) {
            if (ticTacToeBoard[i][0] == symbol && ticTacToeBoard[i][1] == symbol && ticTacToeBoard[i][2] == symbol) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (ticTacToeBoard[0][j] == symbol && ticTacToeBoard[1][j] == symbol && ticTacToeBoard[2][j] == symbol) {
                return true;
            }
        }

        if (ticTacToeBoard[0][0] == symbol && ticTacToeBoard[1][1] == symbol && ticTacToeBoard[2][2] == symbol) {
            return true;
        }

        if (ticTacToeBoard[0][2] == symbol && ticTacToeBoard[1][1] == symbol && ticTacToeBoard[2][0] == symbol) {
            return true;
        }

        return false;
    }

    public void playGame() {
        printBoard();

        while (true) {
            userChance();
            printBoard();
            if (checkWin(humanSymbol)) {
                System.out.println("Human wins!");
                break;
            } else if (checkWin(systemSymbol)) {
                System.out.println("System wins!");
                break;
            } else if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }

            computerChance();
            printBoard();
            if (checkWin(humanSymbol)) {
                System.out.println("Human wins!");
                break;
            } else if (checkWin(systemSymbol)) {
                System.out.println("System wins!");
                break;
            } else if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        NonAiTicTacToe game = new NonAiTicTacToe();
        game.playGame();
    }
}

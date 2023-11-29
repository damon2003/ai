import java.util.*;

public class EigthAStar {
    public static void main(String[] args) {
        int[][] initial = {
                { 1, 2, 3 },
                { 4, 0, 5 },
                { 6, 7, 8 }
        };
        System.out.println("Initial Board:");
        printBoard(initial);
        int[][] goal = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };
        PuzzleState solutionState = solvePuzzle(initial, goal);

        if (solutionState != null) {
            // backtracking and printing the whole set of moves
            System.out.println("");
            List<String> moves = new ArrayList<>();
            PuzzleState currentState = solutionState;
            List<int[][]> states = new ArrayList<>();

            while (currentState.parent != null) {
                // while it not reaches to top, add the moves in moves list and point
                // currentState to its parent
                moves.add(currentState.move);
                states.add(currentState.state);
                currentState = currentState.parent;
            }
            Collections.reverse(moves);

            System.out.println("Blank space movements:");
            for (String move : moves) {
                System.out.println(move);
            }
            for (int[][] state : states) {
                for (int i = 0; i < 3; i++) {
                    System.out.println(Arrays.toString(state[i]));
                }
                System.out.println("");
            }
            System.out.println("");
            System.out.println("Final Board:");
            printBoard(goal);
        } else {
            System.out.println("No solution found.");
        }
    }

    static void printBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static PuzzleState solvePuzzle(int[][] initial, int[][] goal) {
        PriorityQueue<PuzzleState> openSet = new PriorityQueue<>(); // puzzle state's priority queue
        Set<String> closedSet = new HashSet<>();

        // inserting initial node to openSet
        PuzzleState initialNode = new PuzzleState(initial, null, "");
        openSet.add(initialNode);

        while (!openSet.isEmpty()) {
            // getting node with lowest f(n) done by comparedTo()
            PuzzleState currentState = openSet.poll();
            // make it visited
            closedSet.add(Arrays.deepToString(currentState.state));

            // goal state achieved return state
            if (Arrays.deepEquals(currentState.state, goal)) {
                return currentState;
            }

            // store coords of 0 from current state to zeroRow and zeroCol
            int zeroRow = 0, zeroCol = 0;
            outerLoop: for (zeroRow = 0; zeroRow < 3; zeroRow++) {
                for (zeroCol = 0; zeroCol < 3; zeroCol++) {
                    if (currentState.state[zeroRow][zeroCol] == 0) {
                        break outerLoop;
                    }
                }
            }

            // Directions
            int[][] moves = {
                    { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
            };
            String[] moveNames = { "Up", "Down", "Left", "Right" };

            for (int i = 0; i < moves.length; i++) {
                // move zero to each direction
                int newRow = zeroRow + moves[i][0];
                int newCol = zeroCol + moves[i][1];

                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) { // new coords are valid
                    int[][] newState = cloneState(currentState.state);
                    // swapping 0s and After move position
                    newState[zeroRow][zeroCol] = newState[newRow][newCol];
                    newState[newRow][newCol] = 0;

                    PuzzleState newStateNode = new PuzzleState(newState, currentState, moveNames[i]);

                    if (!closedSet.contains(Arrays.deepToString(newState))) {
                        // if newState after move ! visited then add to unvisited
                        openSet.add(newStateNode);
                    }
                }
            }
        }

        return null;
    }

    public static int[][] cloneState(int[][] state) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, 3);
        }
        return newState;
    }
}

class PuzzleState implements Comparable<PuzzleState> {
    // state = current board, parent = before move done board, g=h=f=heuristics
    int[][] state;
    PuzzleState parent;
    String move;
    int gValue;
    int hValue;
    int fValue;

    public PuzzleState(int[][] state, PuzzleState parent, String move) {
        this.state = state;
        this.parent = parent;
        this.move = move;

        // g(n) = the cost of getting from the initial node to n.
        this.gValue = parent == null ? 0 : parent.gValue + 1; // moves done (cost)

        // h(n) = the estimate, according to the heuristic function, of the cost of
        // getting from n to the goal node.
        this.hValue = calculateHeuristic(); // calculates difference between goal state and current state in the form of
                                            // cost

        // f(n) = g(n) + h(n); intuitively, this is the estimate of the best solution
        // that goes through n.
        this.fValue = gValue + hValue; // for comparing with other states (used in compareTo)
    }

    private int calculateHeuristic() {
        int totalDistance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                int value = state[i][j];
                if (value != 0) {
                    // convert values into target dimensions for goal state
                    int targetRow = (value - 1) / 3;
                    int targetCol = (value - 1) % 3;
                    // get difference for rows and columns and add it to totalDistance
                    totalDistance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return totalDistance;
    }

    @Override
    public int compareTo(PuzzleState other) {
        return Integer.compare(this.fValue, other.fValue);
    }
}

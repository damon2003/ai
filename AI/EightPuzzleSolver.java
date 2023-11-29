import java.util.*;

public class EightPuzzleSolver {
    public static int[][] goalState = { { 1, 2, 3 }, { 8, 0, 4 }, { 7, 6, 5 } };
    public static int size = 3;

    public static int calculateHeuristic(int[][] board) { // count += count(unequal node values)
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != goalState[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean isGoalState(int[][] board) {
        return Arrays.deepEquals(board, goalState);
    }

    public static boolean isValidMove(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public static List<PuzzleNode> getNeighbors(PuzzleNode currentState) {
        List<PuzzleNode> neighbors = new ArrayList<>();
        int xBlank = -1;
        int yBlank = -1;

        // Get coord of blank
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (currentState.board[i][j] == 0) {
                    xBlank = i;
                    yBlank = j;
                    break;
                }
            }
        }

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        for (int[] dir : directions) {

            int newX = xBlank + dir[0];
            int newY = yBlank + dir[1];
            // New X and New Y => After changing coord of Blank

            if (isValidMove(newX, newY)) {
                // Create copy of currentState (parameter)
                int[][] newBoard = new int[size][size];
                for (int i = 0; i < size; i++) {
                    newBoard[i] = Arrays.copyOf(currentState.board[i], size);
                }

                // Swap Blank with direction's node value
                newBoard[xBlank][yBlank] = currentState.board[newX][newY];
                newBoard[newX][newY] = 0;

                // count of unequal nodes
                int heuristic = calculateHeuristic(newBoard);
                // storing new board, it's heuristic, cost + 1 (cost is like counter of steps
                // performed) , currentState (Parent)
                neighbors.add(new PuzzleNode(newBoard, heuristic, currentState.cost + 1, currentState));
            }
        }
        return neighbors;
    }

    public static void bestFirstSearch(int[][] initialBoard) {
        PriorityQueue<PuzzleNode> queue = new PriorityQueue<>();
        int initialHeuristic = calculateHeuristic(initialBoard);
        PuzzleNode initialNode = new PuzzleNode(initialBoard, initialHeuristic, 0, null); // create initial puzzlenode
                                                                                          // object
        queue.offer(initialNode); // add into queue

        Set<int[][]> visited = new HashSet<>(); // visited

        while (!queue.isEmpty()) { // until empty
            // When you call poll() to retrieve the next node, it compares nodes to figure
            // out which one has the lowest f-value to return.
            PuzzleNode currentState = queue.poll(); // dequeue
            visited.add(currentState.board); // add to visited Set

            if (isGoalState(currentState.board)) { // check goal state
                System.out.println("Solution found!");
                printPath(currentState);
                return;
            }

            for (PuzzleNode neighbor : getNeighbors(currentState)) { // for every neighbour (by directions),
                // add unvisited neighbours in queue
                if (!visited.contains(neighbor.board)) {
                    // When you add a node to the PriorityQueue using offer(), it will compare the
                    // new node to the existing nodes to figure out where to insert it.
                    queue.offer(neighbor);
                }
            }
        }

        System.out.println("No solution found!");
    }

    public static void printPath(PuzzleNode node) {
        List<PuzzleNode> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        for (int i = path.size() - 1; i >= 0; i--) {
            printBoard(path.get(i).board);
            System.out.println();
        }
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] initialBoard = { { 2, 8, 3 }, { 1, 6, 4 }, { 7, 0, 5 } };
        bestFirstSearch(initialBoard);
    }
}

// Representing board
class PuzzleNode implements Comparable<PuzzleNode> {
    int[][] board; // result board
    int heuristic; // h-value
    int cost; // cost to move
    PuzzleNode parent; // parent board

    public PuzzleNode(int[][] board, int heuristic, int cost, PuzzleNode parent) {
        this.board = board;
        this.heuristic = heuristic;
        this.cost = cost;
        this.parent = parent;
    }

    // Compares f-values (heuristic + cost) of nodes
    // Allows queue to return lowest f-value node first
    @Override
    public int compareTo(PuzzleNode other) {
        /*
         * When you add a node to the PriorityQueue using offer(), it will compare the
         * new node to the existing nodes to figure out where to insert it.
         * When you call poll() to retrieve the next node, it compares nodes to figure
         * out which one has the lowest f-value to return.
         */
        return Integer.compare(this.heuristic + this.cost, other.heuristic + other.cost);
    }
}

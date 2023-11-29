import java.util.*;

public class MazeSolver {
    static int[][] maze = {
            { 0, 0, 1, 0, 1 },
            { 1, 0, 0, 0, 1 },
            { 0, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 1 },
            { 1, 0, 1, 0, 0 }
    };
    static int rows = maze.length;
    static int cols = maze[0].length;

    static int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right

    public static boolean isValidCell(int x, int y) {
        // In bounds and also if path is available/no wall present (==0)
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == 0;
    }

    public static void reconstructPath(Map<Integer, Integer> parents, List<int[]> path, int x, int y) {
        int current = x * cols + y; // 2D -> 1D
        while (parents.containsKey(current)) { // while current (child) present in parents Hashmap
            int parentCell = parents.get(current); // get parent of child node
            int parentX = parentCell / cols; // Extracting x value from parent cell
            int parentY = parentCell % cols; // Extracting y value from parent cell
            path.add(new int[] { parentX, parentY }); // adding to path variable
            current = parentX * cols + parentY; // converting parent into child
        }
        Collections.reverse(path);
    }

    public static boolean solveMazeUsingBFS(int startX, int startY, List<int[]> path) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        Map<Integer, Integer> parents = new HashMap<>();

        // To convert 2D into 1D Array
        // int startCell = startX * cols + startY;
        queue.offer(new int[] { startX, startY }); // enqueue
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // dequeue
            int x = current[0];
            int y = current[1];

            if (x == rows - 1 && y == cols - 1) {
                reconstructPath(parents, path, x, y);
                return true; // Reached the goal
            }

            for (int[] dir : directions) { // { Up : { -1, 0 }, Down: { 1, 0 }, Left : { 0, -1 }, Right: { 0, 1 } };
                int newX = x + dir[0];
                int newY = y + dir[1];
                int newCell = newX * cols + newY; // convert 2d into 1d
                // If cell is valid and not visited
                if (isValidCell(newX, newY) && !visited[newX][newY]) {
                    // Enqueue for continuing loop
                    queue.offer(new int[] { newX, newY });
                    // Set visited true to avoid loop
                    visited[newX][newY] = true;
                    // Add in the Parent Hashmap
                    parents.put(newCell, x * cols + y); // Child Cell, Direct Parent Cell
                }
            }
        }

        return false;
    }

    public static boolean solveMazeUsingDFS(int x, int y, boolean[][] visited, List<int[]> path) {
        if (x == rows - 1 && y == cols - 1) { // till last cell of maze
            path.add(new int[] { x, y }); // added last cell
            return true; // Reached the goal
        }

        visited[x][y] = true;

        // For all directions (Up, Down, Left, Right)
        for (int[] dir : directions) {
            // new move
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (isValidCell(newX, newY) && !visited[newX][newY]) {
                if (solveMazeUsingDFS(newX, newY, visited, path)) {
                    path.add(new int[] { x, y });
                    return true;
                }
            }
        }
        return false;
    }

    public static void printSolutionPath(List<int[]> path) {
        for (int[] point : path) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Select an algorithm:");
            System.out.println("1. Breadth-First Search (BFS)");
            System.out.println("2. Depth-First Search (DFS)");
            choice = scanner.nextInt();

            List<int[]> path = new ArrayList<>();

            switch (choice) {
                case 1:
                    if (solveMazeUsingBFS(0, 0, path)) {
                        System.out.println("BFS: Path found!");
                        System.out.println("BFS Path:");
                        printSolutionPath(path);
                    } else {
                        System.out.println("BFS: No path found.");
                    }
                    break;
                case 2:
                    boolean[][] visited = new boolean[rows][cols];
                    if (solveMazeUsingDFS(0, 0, visited, path)) {
                        System.out.println("DFS: Path found!");
                        System.out.println("DFS Path:");
                        printSolutionPath(path);
                    } else {
                        System.out.println("DFS: No path found.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println("To continue, press 1: ");
            choice = scanner.nextInt();
        } while (choice == 1);
        scanner.close();
    }
}

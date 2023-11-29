import java.util.ArrayList;
import java.util.List;

public class ratMaze {
    private static int[][] maze;
    private static int N; // Number of rows
    private static int M; // Number of columns

    public static List<int[]> findPath(int[][] inputMaze) {
        maze = inputMaze;
        N = maze.length;
        M = maze[0].length;
        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[N][M];
        if (dfs(0, 0, path, visited)) {
            return path;
        }
        return null;
    }

    private static boolean dfs(int row, int col, List<int[]> path, boolean[][] visited) {
        if (row < 0 || row >= N || col < 0 || col >= M || maze[row][col] == 1 || visited[row][col]) {
            return false;
        }

        path.add(new int[] { row, col });
        visited[row][col] = true;

        if (row == N - 1 && col == M - 1) {
            return true; // Reached the destination
        }

        if (dfs(row + 1, col, path, visited) || // Down
                dfs(row, col + 1, path, visited) || // Right
                dfs(row - 1, col, path, visited) || // Up
                dfs(row, col - 1, path, visited)) { // Left
            return true;
        }

        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        int[][] maze = {
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 0, 0, 1 },
                { 1, 1, 0, 0 }
        };
        System.out.println("__________");

        for (int i = 0; i < maze.length; i++) {
            System.out.print("|");
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j] + " ");
            }

            System.out.print("|\n");

        }
        System.out.println("__________");

        List<int[]> path = findPath(maze);
        if (path != null) {
            System.out.println("Path to reach the destination:");
            for (int[] cell : path) {
                System.out.println("(" + cell[0] + ", " + cell[1] + ")");
            }
        } else {
            System.out.println("No path found to reach the destination.");
        }
    }
}
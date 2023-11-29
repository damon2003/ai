import java.util.*;

public class TUF {
    public static boolean graphColoring(List<Integer>[] G, int[] color, int i,
            int C) {
        // Your code here
        int n = G.length; // graph's length
        if (solve(i, G, color, n, C) == true) // loop through all colors and find which one is unique in neighbors
                                              // for passed node
            return true;
        return false;
    }

    private static boolean isSafe(int node, List<Integer>[] G, int[] color, int n, int col) {
        /*
         * 
         * color = {0,0,0,0,0,0,0}
         * col = 1 (4 colors traverse)
         * 
         */

        for (int it : G[node]) { // all adjacent traverse
            if (color[it] == col) // if neighbor color is already given color unsafe/invald color
                return false;
        }

        // no duplicate found!!
        return true;
    }

    private static boolean solve(int node, List<Integer>[] G, int[] color, int n, int m) {
        if (node == n) // if reached till end, path found!
             return true;
        for (int i = 1; i <= m; i++) {
            // traverse through all colors
            if (isSafe(node, G, color, n, i)) { // iscurrent color is unique in neighbors
                color[node] = i; // set color to current node
                if (solve(node + 1, G, color, n, m) == true) // recursion with next node
                    return true;
                color[node] = 0;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int N = 7, M = 4; // 7 nodes and4 colors
        List<Integer>[] G = new ArrayList[N]; // adjancency list of graph
        for (int i = 0; i < N; i++) {
            G[i] = new ArrayList<>();
        }
        G[0].add(1);
        G[0].add(2);
        G[1].add(2);
        G[1].add(3);
        G[1].add(0);
        G[2].add(1);
        G[2].add(0);
        G[2].add(3);
        G[2].add(4);
        G[2].add(5);
        G[3].add(4);
        G[3].add(4);
        G[3].add(2);
        G[3].add(1);
        G[4].add(5);
        G[4].add(3);
        G[4].add(2);
        G[5].add(2);
        G[5].add(4);
        G[5].add(6);
        G[6].add(5);
        int[] color = new int[N]; // created color array for 7 nodes
        boolean ans = graphColoring(G, color, 0, M);
        if (ans) {
            System.out.println("Graph coloring is possible");
            System.out.println("Node colors:");
            for (int i = 0; i < N; i++) {
                System.out.println("Node " + i + " is colored with " + color[i]);
            }
        } else {
            System.out.println("Graph coloring is not possible with " + M + " colors.");
        }
        // if (ans == true)
        //     System.out.println("1");
        // else
        //     System.out.println("0");
    }
}

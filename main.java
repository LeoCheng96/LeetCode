public class Main {
    public static void main(String args[]) {
        int[][] oneCakeCosts = {{1,2,3}}; // return 1 as the lowest cost
        int[][] twoCakeCosts = {{1,2,3}, {4, 2, 2}}; // return 1 + 2 = 3
        int[][] threeCakeCosts = {{1,2,3}, {4, 2, 2}, {5, 3, 1}}; // return 1 + 2 + 1 = 4
        int[][] fourCakeCosts = {{1, 1, 2}, {2, 1, 3}, {5, 3, 1}, {4, 3, 1}}; // return 1 + 1 + 3 + 1 = 6

        System.out.println("Minimum cost to paint 1 cake:"
                + paintCakes(oneCakeCosts));
        System.out.println("Minimum cost to paint 2 cakes:"
                + paintCakes(twoCakeCosts));
        System.out.println("Minimum cost to paint 3 cakes:" +
                paintCakes(threeCakeCosts));
        System.out.println("Minimum cost to paint 4 cakes:" +
                paintCakes(fourCakeCosts));
    }
    
    // Assuming there are n cakes
    // Runtime O(6(3n)) because we try 6 combinations of the first and last cake
    // and each iteration we iterate over the 3xn costs array
    // Space O(3n) for the copy array

    public static int paintCakes(int[][] costs) {
        if (costs.length == 0) throw new RuntimeException("no cakes to paint!");
        if (costs.length == 1) return Math.min(costs[0][0], Math.min(costs[0][1], costs[0][2]));

        int minCost = Integer.MAX_VALUE;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (i==j) continue;
                int[][] copy = copy(costs);
                copy[1][i] = 1000000;
                copy[copy.length-2][j] = 1000000;
                minCost = Math.min(minCost, costs[0][i] + costs[costs.length-1][j] + paintMiddleCakes(copy));
            }
        }

        return minCost;
    }

    public static int[][] copy(int[][] costs) {
        int [][] copy = new int[costs.length][];
        for(int i = 0; i < costs.length; i++)
        {
            int[] copyArr = costs[i];
            int length = copyArr.length;
            copy[i] = new int[length];
            System.arraycopy(copyArr, 0, copy[i], 0, length);
        }

        return copy;
    }

    public static int paintMiddleCakes(int[][] costs) {

        if (costs.length == 2) return 0;

        int n = costs.length-2;

        for (int i=2; i<=n; i++) {
            costs[i][0] += Math.min(costs[i-1][1],costs[i-1][2]);
            costs[i][1] += Math.min(costs[i-1][0],costs[i-1][2]);
            costs[i][2] += Math.min(costs[i-1][1],costs[i-1][0]);
        }
        return Math.min(Math.min(costs[n][0], costs[n][1]), costs[n][2]);
    }
}

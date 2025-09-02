// hacked from https://www.geeksforgeeks.org/traveling-salesman-problem-tsp-implementation/

/*

Travelling Salesman Problem (TSP)
=================================

Given a set of cities and the distance between every pair of cities, the problem is to find 
the shortest possible route that visits every city exactly once and returns to the starting point. 

Note the difference between Hamiltonian Cycle and TSP.
* In the Hamiltonian cycle problem, the goal is to find if there exists a tour that visits every city exactly once.
* In the TSP, we know that Hamiltonian Tour exists because the graph is complete. See TSP.png. In fact,
many such tours exist; the problem is to find the minimum weight Hamiltonian Cycle.

For example, consider the graph shown in file TSP.png.
Some possible tours:
1-2-4-3-1 at a cost of 80.
4-3-1-2-4 at a cost of 80.
1-3-4-2-1 at a cost of 80.
1-2-3-4-1 at a cost of 95.

Algorithm high-level flow:
* Consider city 1 (circled 1) as the starting and ending point. Note that since the route is cyclic,
  we we could have considered any point as a starting point.
* Generate all (n-1)! permutations of cities.
* Calculate the cost of every permutation and keep track of the minimum cost permutation.
* Return the permutation with minimum cost.

 */

// Java program to implement 
// traveling salesman problem 
// using naive approach.

import java.util.*;

class main {

    static int maxCities = -1; // number of cities to visit just once

    // Function to swap the data present in the left and right indices
    public static ArrayList<Integer> swap(ArrayList<Integer> data, int left, int right) {
		// Swap the data
        int temp = data.get(left);
        data.set(left, data.get(right));
        data.set(right, temp);

		// Return the updated array
        return data;
    }

    // Function to reverse the sub-array, starting from left to the right, both inclusive.
    public static ArrayList<Integer> reverse(ArrayList<Integer> data, int left, int right) {
		// Reverse the sub-array
        while (left < right) {
            int temp = data.get(left);
            data.set(left++, data.get(right));
            data.set(right--, temp);
        }

		// Return the updated array
        return data;
    }

    // Function to find the next permutation of the given integer array.
    public static boolean findNextPermutation(ArrayList<Integer> data) {
		// If the given dataset is empty
		// or contains only one element
		// next_permutation is not possible
        if (data.size() <= 1)
            return false;

        int last = data.size() - 2;

		// find the longest non-increasing suffix and find the pivot.
        while (last >= 0) {
            if (data.get(last) < data.get(last + 1)) {
                break;
            }
            last--;
        }

		// If there is no increasing pair, there is no higher-order permutation.
        if (last < 0)
            return false;

        int nextGreater = data.size() - 1;

		// Find the rightmost successor to the pivot.
        for (int ii = data.size() - 1; ii > last; ii--) {
            if (data.get(ii) > data.get(last)) {
                nextGreater = ii;
                break;
            }
        }

		// Swap the successor and the pivot.
        data = swap(data, nextGreater, last);

		// Reverse the suffix.
        data = reverse(data, last + 1, data.size() - 1);

		// Return true as the next_permutation is done.
        return true;
    }

    // Given a configuration of cities and the starting city,
    // solve the Travelling Salesman Problem.
    static int theTravellingSalesmanProblem(int graph[][], int startCity) {
        // otherCities = all cities except the starting city.
        ArrayList<Integer> otherCities = new ArrayList<Integer>();
        for (int ii = 0; ii < maxCities; ii++)
            if (ii != startCity)
                otherCities.add(ii);

        // Initialise minimum path weight to the worst case.
        int minWeight = Integer.MAX_VALUE;

        // Loop over all possible permutations of cities, based on the starting city.
        do {
            // Initialise the current path weight(cost)
            int currentPathWeight = 0;

            // compute current path weight
            int kk = startCity;
            System.out.printf("%d", kk+1);

            for (int ii = 0; ii < otherCities.size(); ii++) {
                currentPathWeight += graph[kk][otherCities.get(ii)];
                kk = otherCities.get(ii);
                System.out.printf("-%d", kk+1);
            }
            currentPathWeight += graph[kk][startCity];
            System.out.printf("   %d\n", currentPathWeight);

            // update minimum
            minWeight = Math.min(minWeight, currentPathWeight);

        } while (findNextPermutation(otherCities));

        return minWeight;
    }

    // main function
    public static void main(String args[]) {
        int startCity = 0;
		// graph = a matrix representation of the inter-city graph graph with starting city = 0.
        int graph0[][] = {{0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}};
        maxCities = graph0[0].length; // number of columns
        System.out.printf("Number of cities = %d, start city = %d\nGraph:\n", maxCities, startCity);
        for (int i = 0; i < graph0.length; i++) {
            for (int j = 0; j < graph0[i].length; j++) {
                System.out.print(graph0[i][j]);
                System.out.print("\t");
            }
            System.out.println(); // New line after each row
        }
        System.out.printf("Cheapest route: %d\n", theTravellingSalesmanProblem(graph0, startCity));
        
        Checkers.theEnd(0);
    }
}

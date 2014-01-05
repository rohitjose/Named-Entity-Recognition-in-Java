/**
 * Combination is a class that contains the methods to return all the possible
 * combinations of indices  depending on the size specified
 */
package com.rohit.ner;

import java.util.ArrayList;

public class Combination {

	public static boolean combine(int[] c, int n) {
		//Checks for the combinations possible on the array passed
		for (int i = c.length; --i >= 0;) {
			if (++c[i] <= n - (c.length - i)) {
				for (; ++i < c.length;)
					c[i] = c[i - 1] + 1;
				return true;
			}
		}
		return false;
	}

	public ArrayList<int[]> setCombinations(int Size) {
		// Returns the list of integer arrays that represents the list of index cominations that can
		// occur for the specified size
		int N = Size;
		ArrayList<int[]> combinations = new ArrayList<int[]>();//initializes the arraylist to capture the index list combinations

		for (int n = 1; n <= N; n++) {
			int[] a = new int[n];//creates a new integer array
			for (int i = 0; i < a.length; i++)
				a[i] = i;//assigns the possible index values based on the current size

			do {

				int[] b = new int[a.length];
				/* Creates a new integer array - done to avoid the reference being copied onto the arraylist combinations
				 * if a is assigned directly as the loop progresses the values in the arraylist keeps changing
				 * based on the values of the reference assigned. When a new reference is created and assigned each time
				 * the issue is resolved
				 */
				System.arraycopy(a, 0, b, 0, a.length);//copies data from a to b
				combinations.add(b);// adds the new combination to the list
			} while (combine(a, N));
		}

		return combinations;//returns the final list of possible indices
	}

}

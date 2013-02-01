import java.util.ArrayList;
import java.util.Scanner;

/**
 * Try-out problem 2 solution. This is my first attempt at a solution. First it
 * finds all the possible powers of 2 that are less than N. Then, it creates a
 * ternary truth table with one column for each possible options, and multiplies
 * each option by the corresponding value from the table. For example: 
 * 
 * N = 5
 * options = {1, 2, 4} 
 * table = { 2 2 2 - 2*1 + 2*2 + 2*4 = 14 
 * 			 2 2 1 
 * 			 2 2 0 
 * 			 2 1 2
 * 			 2 1 1 
 * 			 2 1 0 
 * 			 2 0 2 
 * 			 2 0 1 
 *           2 0 0 
 *           1 2 2 
 *           1 2 1 
 *           1 2 0 - 1*1 + 2*2 + 0*4 = 5 **ONE SOLUTION*** 
 *           ..... 
 *           ..... 
 *           0 0 0 }
 * 
 * This solution is not acceptable for inputs that are very large. It 
 * takes an unacceptable amount of time for inputs where N is very large. 
 * This is because my solution is a brute-force solution. I should
 * not be generating every possible combination of the powers of two. 
 * For example, the largest possible input (2^31) will have 31 powers
 * of two. If I generate this table, I will be computing 3^31 possible
 * sums. Doing this will take an unacceptable amount of time. A more efficient
 * solution would generate the options using a prefix tree, where we would
 * add to a common sum after each level in the trie. However, I have never
 * used this data structure and I wasn't sure if I was heading in the right
 * direction with that idea so I decided not to implement it.
 * 
 * @author Karthik Kumar (kkumar91@vt.edu)
 * 
 */
public class Powers {
	/* The list of powers less than N */
	private static ArrayList<Integer> options;
	private static int count = 0;
	/* The number we are looking to add up to*/
	private static long N;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int numOfCases = input.nextInt();
		for (int j = 0; j < numOfCases; j++) {
			N = input.nextInt();
			options = getPowersLessThan(N);
			int size = options.size();
			calculate(0, size, new int[size]);
			System.out.println(count);
		}
	}
	/**
	 * Recursive function to calculate each row of the truth table and 
	 * find the value that can be generated from the row. If we find a match,
	 * we print it out, otherwise, keep going until the entire table is 
	 * parsed
	 * @param index The current index of value in the row 
	 * @param size The size of the column of the truth table (number of powers 
	 * 		less than N)
	 * @param current The row of the table we have currently
	 */
	private static void calculate(int index, int size, int[] current) {
		if (index == size) {
			int sum = 0;
			for (int i = 0; i < size; i++) {
				sum += current[i] * options.get(i);
			}
			if (sum == N) {
//				for (int i = 0; i < size; i++) {
//					if (current[i] == 0) {
//						continue;
//					} else if (current[i] == 1) {
//						System.out.print(options.get(i) + " + ");
//					} else if (current[i] == 2) {
//						System.out.print(options.get(i) + " + "
//								+ options.get(i) + " + ");
//					}
//				}
//				//If we have gone through the row, remove the " + "
//				System.out.println("\b\b= " + N);
				count++;
			}
		} else {
			for (int i = 2; i >= 0; i--) {
				current[index] = i;
				calculate(index + 1, size, current);
			}
		}
	}

	/**
	 * Returns all the powers of 2 that are less than N
	 * @param n
	 * @return List of powers less than 2
	 */
	private static ArrayList<Integer> getPowersLessThan(long n) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 31; i++) {
			double pow = Math.pow(2, i);
			if (pow <= n) {
				list.add((int) pow);
			}
		}
		return list;
	}
}
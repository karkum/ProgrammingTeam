import java.util.Scanner;
/**
 * Simple goes through and places the value we are looking at by counting
 * the number of zeros before it and placing it if we the number of locations with zeros= 
 * the input value for that index location
 *
 * Submitted to ICPC site, took 2.460 seconds. 
 * 
 * Karthik Kumar
 *
 */

public class Permutations {
	public static void main(String [] args) {
		System.out.println(-27%26);
		Scanner scan = new Scanner(System.in);
		while (true) {
			int N = scan.nextInt();
			if (N == 0)
				break;
			int [] arr = new int[N];
			for (int i = 0; i < N; i++) {
				arr[i] = scan.nextInt();
			}
			printArray(solve(arr));
		}
	}

	private static void printArray(int[] arr) {
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < arr.length - 1; i++) {
			buff.append(arr[i] + ",");
		}
		buff.append(arr[arr.length - 1]);
		System.out.println(buff.toString());
	}

	private static int[] solve(int[] arr) {
		int [] perm = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int loc = 0;
			int val = arr[i];
			//start count at -1 to avoid off by one error
			int count = -1;
			for (int j =0; j < perm.length; j++) {
				if (perm[j] == 0)
					count++;
				if (val == count) {
					perm[j] = i+1;
					break;
				}
			}
			
			//old and first solution, bad because we are looping from 0 to length each time
//			if (val == 0)
//				loc = findFirstZero(perm);
//			else {
//				loc = findNthZero(perm, val);
//			}
//			perm[loc] = i+1;
		}
		return perm;
	}
	@SuppressWarnings("unused")
	private static int findNthZero(int[] perm, int val) {
		int count = 0;
		for (int i = 0; i < perm.length; i++) {
			if (perm[i] == 0)
				count++;
			if (count == val && perm[(i+1)] == 0) {
				return i + 1;
			}
		}
		return -1;
	}
	@SuppressWarnings("unused")
	private static int findFirstZero(int[] perm) {
		for (int i = 0; i<perm.length; i++) {
			if (perm[i]==0)
				return i;
		}
		return -1;
	}
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * Karthik Kumar
 * passes test0.in, test1.in, test2.in, fails on the last input of test3.in 
 * (i get 59 not 58)
 * 
 * Efficiency is low. Would have been improved by caching results we have
 * previously calculated.
 * 
 * @author karthik
 *
 */
public class WordStack {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
//		String[] arrr = { "aaa", "abc", "bcd", "bfcde", "cde"};
//		calculateScore(arrr);
		while (true) {
			int N = scan.nextInt();
			if (N == 0) {
				break;
			}
			String[] arr = new String[N];
			for (int i = 0; i < N; i++) {
				arr[i] = scan.next();
			}
			ArrayList<String> curr = new ArrayList<String>();
			ArrayList<String> remaining = new ArrayList<String>();
			ArrayList<Integer> numbers = new ArrayList<Integer>();
			for (String s : arr) {
				remaining.add(s);
			}
			solve(curr, remaining, numbers);
			System.out.println(Collections.max(numbers));
		}
	}

	/**
	 * Permute the different combinations of string, and calculate the scores.
	 * @param curr
	 * @param remaining
	 * @param scores
	 */
	public static void solve(ArrayList<String> curr, ArrayList<String> remaining,
			ArrayList<Integer> scores) {
		if (remaining.size() == 0) {
			String [] arrrr = new String[curr.size()];
			for (int i = 0; i < curr.size(); i++)
				arrrr[i] = curr.get(i);
			int score = calculateScore(arrrr);
			scores.add(score);
		} else {
			for (int i = 0; i < remaining.size(); i++) {
				curr.add(remaining.remove(i));
				solve(curr, remaining, scores);
				remaining.add(i, curr.remove(curr.size() - 1));

			}
		}
	}

	/**
	 * This function calculates the max possible score for an array of words.
	 * It tries to place the words in the correct position with any spaces
	 * before it to maximize the score. 
	 * @param arr
	 * @return
	 */
	public static int calculateScore(String[] arr) {
		int total = 0;
		// loop through all the strings in the array.
		for (int i = 1; i < arr.length; i++) {
			String above = arr[i - 1];
			String thiString = arr[i];
			int aboveLength = above.length();
			int max = 0;

			// loop through the string above
			for (int j = 0; j < aboveLength; j++) {
				int count = 0;
				// loop thorugh this string, seeing where we can place it
				// if we find a pint, then break out of these two words
				int missed = 0;
				for (int k = 0; k < thiString.length(); k++) {
					if (missed != 1 && above.charAt(j) == thiString.charAt(k)) {
						count++;
						j++;
						if (j >= aboveLength)
							break;
						// break;
					} else {
						missed++;
					}
				}
				if (count > max) {
					max = count;
//					System.out.println("point: " + above + ", " + thiString
//							+ " " + count);
				}
			}
			total += max;
		}
//		System.out.println(total);
		return total;
	}
}

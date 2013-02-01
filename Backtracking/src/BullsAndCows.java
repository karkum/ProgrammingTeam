import java.util.ArrayList;
import java.util.Scanner;

/**
 * Karthik Kumar
 *
 * This solution is based on the first one we looked at in class. 
 * It works for the large input in less 
 * than 3 seconds and works for both inputs. The algorithm is to generate
 * all the possible combinations of numbers and see if they match the input
 */
public class BullsAndCows {
	static ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		ArrayList<Guess> guesses = new ArrayList<Guess>();
		for (;;) {
			int N = scan.nextInt();
			if (N == 0) {
				break;
			}
			while (true) {
				int i = scan.nextInt();
				if (i == -1) {
					@SuppressWarnings("unused")
					String garbage = scan.nextLine();
					break;
				}
				ArrayList<Integer> arr = new ArrayList<Integer>(N);
				arr.add(i);
				for (int j = 1; j < N; j++) {
					arr.add(scan.nextInt());
				}
				int bulls = scan.nextInt();
				int cows = scan.nextInt();
				Guess g = new Guess(arr, bulls, cows);
				guesses.add(g);
			}
			
			ArrayList<Integer> permutations = new ArrayList<Integer>(10);
			for (int j = 0; j < 10; j++)
				permutations.add(j);
			ArrayList<Integer> options = new ArrayList<Integer>();
			solve(guesses, permutations, options, N);
			for (int k = 0; k < result.get(0).size(); k++) {
				System.out.print(result.get(0).get(k));
			}
			System.out.println(" is one of " + result.size()
					+ " possible solutions.");
			result.clear();
			guesses.clear();
		}
	}

	private static void solve(ArrayList<Guess> guesses,
			ArrayList<Integer> perm, ArrayList<Integer> options, int n) {
		if (options.size() == n) {
			// System.out.println(Arrays.toString(options.toArray()));
			boolean works = true;
			for (Guess g : guesses) {
				if (!matchesBullCowCount(g, options)) {
					works = false;
					break;
				}
			}
			if (works) {
				// System.out.println("WORKS");
				result.add((ArrayList<Integer>) options.clone());
			}
		} else {
			//Add numbers to the list, solve and then remove the number we added
			for (int i = 0; i < perm.size(); i++) {
				options.add(perm.remove(i));
				solve(guesses, perm, options, n);
				perm.add(i, options.remove(options.size() - 1));
			}
		}
	}

	public static class Guess implements Comparable<Guess> {
		ArrayList<Integer> nums;
		int bulls;
		int cows;

		public Guess(ArrayList<Integer> a, int b, int c) {
			nums = a;
			bulls = b;
			cows = c;
		}

		@Override
		public int compareTo(Guess other) {
			return bulls - other.bulls;
		}

	}

	private static boolean matchesBullCowCount(Guess guess,
			ArrayList<Integer> arr) {
		int totalCount = guess.bulls + guess.cows;
		int bullCount = guess.bulls;
		int thisBullCount = 0;
		int thisTotal = 0;
		for (int i = 0; i < guess.nums.size(); i++) {
			if (guess.nums.get(i) == arr.get(i)
					|| guess.nums.contains(arr.get(i)))
				thisTotal++;
			if (guess.nums.get(i) == arr.get(i))
				thisBullCount++;
		}
		return thisTotal == totalCount && thisBullCount == bullCount;
	}
}

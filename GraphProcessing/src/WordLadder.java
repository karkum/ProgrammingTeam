import java.util.ArrayList;
import java.util.Scanner;

/**
 * Passes on icpc site. 1.356 seconds
 * @author karthik
 *
 */
public class WordLadder {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int numCases = Integer.valueOf(sc.nextLine());
			if (numCases == 0)
				break;
			ArrayList<String> strings = new ArrayList<String>();
			for (int i = 0; i < numCases; i++) {
				strings.add(sc.nextLine());
			}
			solve(strings);
		}
	}

	private static void solve(ArrayList<String> strings) {
		int path[][] = new int[strings.size()][strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			String start = strings.get(i);
			for (int j = 0; j < strings.size(); j++) {
				int result = 1000;
				String end = strings.get(j);
				if (i == j)
					continue;
				if (start.length() == end.length()) {
					result = change(start, end);
					path[i][j] = result;
				} else if (start.length() == end.length() - 1) {
					result = add(start, end);
					path[i][j] = result;
				} else if (start.length() == end.length() + 1) {
					result = remove(start, end);
					path[i][j] = result;
				} else {
					path[i][j] = result;
				}
			}
		}
		floydsWarshall(path);
		System.out.println(findMax(path) + 1);
	}

	private static int findMax(int[][] path) {
		int max = 0;
		for (int i = 0; i < path.length; i++) {
			for (int j = 0; j < path.length; j++) {
				if (path[i][j] != -1 && path[i][j] != 1000 && path[i][j] > max)
					max = path[i][j];
			}
		}
		return max;
	}

	private static int remove(String start, String end) {
		for (int i = 0; i < start.length(); i++) {
			if ((start.substring(0, i) + start.substring(i + 1)).equals(end))
				return 1;
		}
		// did not work
		return 1000;
	}

	private static int add(String start, String end) {
		for (int i = 0; i < end.length(); i++) {
			if ((start.substring(0, i) + end.charAt(i) + start.substring(i))
					.equals(end))
				return 1;
		}
		// did not work
		return 1000;
	}

	private static int change(String start, String end) {
		for (int i = 0; i < start.length(); i++) {
			if ((start.substring(0, i) + end.charAt(i) + start.substring(i + 1))
					.equals(end))
				return 1;
		}
		// did not work
		return 1000;
	}

	private static void floydsWarshall(int[][] path) {
		int n = path.length;
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (path[i][k] != 0 && path[i][k] != 1000
							&& path[k][j] != 0 && path[k][j] != 1000
							&& path[i][k] + path[k][j] < path[i][j]) {
						path[i][j] = path[i][k] + path[k][j];
					}
				}
			}
		}
	}
}

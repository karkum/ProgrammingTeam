import java.util.ArrayList;
import java.util.Scanner;

public class K {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = 0;
		ArrayList<String> buff = new ArrayList<String>();
		while (sc.hasNext()) {
			String line = sc.nextLine();
			int len = line.length();
			if (N < len)
				N = len;
			buff.add(line);
		}
		buff.remove(buff.size() - 1);
		System.out.println(solve(buff, N));
	}

	private static int solve(ArrayList<String> strings, int n) {
		int score = 0;
		for (String s : strings) {
			int m = s.length();
			double thisScore = Math.pow((double) (n - m), 2);
			score += (int) (thisScore);
		}
		return score;
	}
}
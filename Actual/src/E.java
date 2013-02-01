import java.util.Arrays;
import java.util.Scanner;

public class E {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			if (N == 0)
				break;
			String [] arr = new String[N];
			for (int i = 0; i < N; i++) {
				arr[i] = sc.next();
			}
			Arrays.sort(arr);
			System.out.println(solve(arr[arr.length/2 - 1], arr[arr.length/2]));
		}
	}

	private static String solve(String s, String t) {
		String result = "";
		int minLength = Math.min(s.length(), t.length());
		for (int i = 0; i < minLength; i++) {
			char first = s.charAt(i);
			char second = t.charAt(i);
			if (i == t.length() - 1) {
				int dist = Math.abs(second-first);
				if (dist > 1) {
					if ((result+first).compareTo(s) < 0) {
						result += (char)((int)first+1);
						break;
					}
					result += first;
				}
				else {
					int diff = s.length() - t.length();
					if (diff >= 2) {
						char add = (char)(s.charAt(i+1)+1);
						if (add > 'Z') {
							int ind = s.lastIndexOf('Z');
							if (ind < s.length() - 2) {
								result = s.substring(0, ind+1) + (char)(s.charAt(ind+1)+1);
							} else {
								result = s;
							}
						} else {
							result = s.substring(0, i+1) + (add);
						}
						break;
					}
					result = ""+s;
				}
				break;
			}
			if (i == s.length() - 1) {
				int dist = Math.abs(second-first);
				if (dist > 1)
					result += first;
				else
					result = ""+t;
				break;
			}
			if (first == second) {
				result+= ""+(char)first;
			} else {
				if (first != 'Z') { 
					if (first < second) {
						result += ""+(char)((int)first+1);
					}
					break;
				}
			}
		}
		if (result.equals(t))
			result = s;
		return result;
	}
}
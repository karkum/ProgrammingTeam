import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Passes ICPC judge. places each letter one by one until we find answer.
 * @author karthik
 *
 */
public class StringerMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int N = sc.nextInt();
			if (N == 0) {
				if (sc.next().equals("0"))
					break;
			}
			BigInteger K = new BigInteger(sc.next());
			int arr[] = new int[N];
			for (int i = 0; i < N; i++) {
				arr[i] = sc.nextInt();
			}
			StringBuilder str = new StringBuilder();

			for (int i = 0; i < N; i++) {
				int counti = arr[i];
				for (int j = 0; j < counti; j++) {
					str.append((char) (i + 'a'));
				}
			}
			String fixed = "";
			BigInteger lastInd = BigInteger.ZERO;
			solve(str.toString(), fixed, lastInd, K);
		}
	}

	private static void solve(String inputString, String fixed, BigInteger lastInd,
			BigInteger K) {
		//each step, i want to place a letter
		//once i place it, i send off rest of string
		//each call, place a letter for a sure
		//in each call, go through and place each letter.
		if (inputString.length() == 0) {
			System.out.println(fixed);
			return;		
		}
		String tryFixing = null;
		BigInteger newInd = BigInteger.ZERO;
		String newStringWOFixed = null;
		HashSet<Character> tried = new HashSet<Character>();
		for (int i = 0; i < inputString.length(); i++) {
			if (!tried.contains(inputString.charAt(i))) {
				tried.add(inputString.charAt(i));
				tryFixing = fixed + inputString.charAt(i);
				newStringWOFixed = getStringWO(inputString, i);
				BigInteger thisPerm = getNumPerms(newStringWOFixed.length(),
						newStringWOFixed.toCharArray());
				if (thisPerm.add(lastInd).compareTo(K) > 0) {
					newInd = lastInd.add(BigInteger.ZERO);
					break;
				} 
				else {
					lastInd = thisPerm.add(lastInd);
				}
			}
		}
		solve(newStringWOFixed, tryFixing, newInd, K);
	}

	private static String getStringWO(String str, int i) {
		return str.substring(0, i) + str.substring(i+1);
	}
	private static BigInteger getNumPerms(int n, char[] arr) {
		BigInteger numerator = fac(n);
		HashSet<Character> unique = new HashSet<Character>();
		BigInteger denom = BigInteger.ONE;
		for (char ch : arr) {
			if (!unique.contains(ch))
			{
				int freq = getFrequencies(arr, ch);
				denom = denom.multiply(fac(freq));
				unique.add(ch);
			}
		}
		return numerator.divide(denom);
	}

	private static int getFrequencies(char[] arr, char c) {
		int count = 0;
		for (char ch : arr) {
			if (ch == c)
				count++;
		}
		return count;
	}

	private static BigInteger fac(int n) {
		BigInteger r = BigInteger.ONE;
		for (int i = 1; i <= n; i++) {
			r = r.multiply(BigInteger.valueOf(i));
		}
		return r;
	}
}

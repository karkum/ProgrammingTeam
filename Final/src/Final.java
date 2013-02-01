import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Final problem solution. Fixed my issue with carry. Passes sample input
 * @author karthik
 *
 */
public class Final {
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			if (n == 0 && m == 0)
				break;
			if (m == 0) {
				System.out.println("1");
				continue;
			}
			boolean [] primes = sieve(n);
			ArrayList<Integer> ans = solve(n, m, primes);
			for (Integer i : ans) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}

	private static ArrayList<Integer> solve(int n, int m, boolean primes[]) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < primes.length; j++) {
			int p;
			if (primes[j] == true) {
				p = j;
				int [] newm = toBasep(m, p);
				int [] nminusm = toBasep(n-m, p);

				int carries = findNumCarries(newm, nminusm, p);
				for (int i = 0; i < carries; i++) {
					list.add(p);
				}
			}
		}
		return list;
	}	

	private static int[] toBasep(int n, int p) {
		ArrayList<Integer> nums = new ArrayList<Integer>();
		while (n>0) {
			nums.add(0,n%p);
			n = n/p;
		}
		int [] arr = new int[nums.size()];
		for (int i = 0; i < nums.size(); i++)
			arr[i] = nums.get(i);
		return arr;
	}
	private static int findNumCarries(int[] newm, int[] nminusm, int radix) {
		int count = 0;
		int[] longer;
		int[] shorter;
		if (newm.length != nminusm.length) {
			if (newm.length >= nminusm.length) {
				longer = newm;
				shorter = nminusm;
			}
			else {
				longer = nminusm;
				shorter = newm;
			}
			int diff = longer.length - shorter.length;
			int [] temp = new int[longer.length];
			for (int i = 0; i < shorter.length; i++) {
				int index = i + diff;
				temp[index] = shorter[i];
			}
			shorter = temp;
		} else {
			longer = newm;
			shorter = nminusm;
		}
		boolean carry = false;
		for (int i = longer.length - 1; i >= 0; i--) {
			int sum = (shorter[i] + longer[i]);
			if (carry) {
				sum += 1;
			}
			if (sum >= radix) {
				count++;
				carry = true;
			} else {
				carry = false;
			}
		}
		return count;
	}

	public static boolean[] sieve(int N)
	{
		boolean[] isPrime = new boolean[N + 1];
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;
		// Iterate through all numbers up to sqrt(N) to
		// locate all of the prime numbers.
		for (int i = 2; i * i <= N; i++)
		{
			if (!isPrime[i])
			{
				continue;
			}
			// Iterate through all multiples of the prime and mark them as
			// not prime.
			for (int multiples = i*i; multiples <= N; multiples += i)
			{
				isPrime[multiples] = false;
			}
		}
		return isPrime;
	}

}

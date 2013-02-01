import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class E {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String line = sc.nextLine();
			solve(line);
		}
	}

	private static void solve(String line) {
		String[] initial = line.split(" ");
		int num = Integer.valueOf(initial[0]);
		int [] arr = new int[num];
		for (int i = 0; i < num; i++) {
			arr[i] = Integer.valueOf(initial[i+1]);
		}
		Arrays.sort(arr);
		int j =0;
		for (j = arr.length - 1; j >= 0; j--) {
			if (isOk(j, arr))
				break;
		}
		System.out.println(arr[j]);
	}

	private static boolean isOk(int j, int[] arr) {
		BigInteger thisOne = new BigInteger(""+arr[j]);
		for (int i = 0; i < arr.length; i++) {
			if (i!=j) {
				BigInteger otherOne = new BigInteger(""+arr[i]);
				if(!thisOne.gcd(otherOne).equals(BigInteger.ONE)) {
					return false;
				}
			}
		}
		return true;
	}
}

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Accepted on Kattis site
 * @author karthik
 *
 */
public class J {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numRooms = Integer.valueOf(sc.next());
		int[][] capacity = new int[numRooms][numRooms];
		int [] input = new int[numRooms];
		for (int i = 0; i < numRooms; i++) {
			input[i] = Integer.valueOf(sc.next());
		}
		Arrays.sort(input);
		
		for (int i = 0; i < input.length; i++) {
			for (int j = i+1; j < input.length; j++) {
				
				int gcd = ((new BigInteger(input[i] + ""))
						.gcd(new BigInteger(input[j] + ""))).intValue();
				if (gcd <= 1) {
					capacity[i][j] = 0;
					capacity[j][i] = 0;
				}
				else {
					capacity[i][j] = gcd;
					capacity[j][i] = gcd;

				}
			}
		}
		System.out.println(edmundsKarp(capacity, 0, numRooms - 1));
	}

	private static int edmundsKarp(int[][] capacity, int s, int t) {
		int f = 0;
		while (true) {
			int [] pred = new int[capacity.length];
			Arrays.fill(pred, -1);
			int m = bfs(capacity, s, t, pred);
			if (m == 0)
				break;
			f += m;
			int v = t;
			while (v != s) {
				int u = pred[v];
				capacity[u][v] = capacity[u][v] - m;
				capacity[v][u] = capacity[v][u] - m;
				v = u;
			}
		}
		return f;
	}

	private static int bfs(int[][] capacity, int s, int t, int[] pred) {
		pred[s] = -2;
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		int [] M = new int[capacity.length];
		M[s] = Integer.MAX_VALUE;
		q.add(s);
		while (!q.isEmpty()) {
			int u = q.poll();
			for (int v = 0; v < capacity.length; v++) {
				if (capacity[u][v] > 0 && pred[v] == -1) {
					pred[v] = u;
					M[v] = Math.min(M[u], capacity[u][v]);
					if (v != t) {
						q.addLast(v);
					} else {
						return M[t];
					}
				}
			}
		}
		return 0;
	}
}

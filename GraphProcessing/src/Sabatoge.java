import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Sabatoge {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String line = sc.nextLine();
			int numOfCities = Integer.valueOf(line.split(" ")[0]);
			int start = 0;
			int end = 1;
			int[][] capacity = new int[numOfCities][numOfCities];
			int numConnections = Integer.valueOf(line.split(" ")[1]);
			if (numConnections == 0 && numOfCities == 0)
				break;
			for (int i = 0; i < capacity.length; i++) {
				Arrays.fill(capacity[i], -1);
			}
			for (int i = 0; i < numConnections; i++) {
				String thisLine = sc.nextLine();
				String [] arr = thisLine.split(" ");
				int orig = Integer.valueOf(arr[0]) - 1;
				int dest = Integer.valueOf(arr[1]) - 1;
				int weight = Integer.valueOf(arr[2]);
				capacity[orig][dest] = weight;
				capacity[dest][orig] = weight;
			}
			edmundsKarp(capacity, start, end);
			HashSet<Integer> capital = minCut(capacity, start, end);
			for (int v : capital) {
				for (int w = 0; w < capacity.length; w++) {
					if (capacity[v][w] == 0 && !capital.contains(w))
						System.out.println((v+1) + " " + (w+1));
				}
			}
			System.out.println();	
		}
	}
	private static HashSet<Integer> minCut(int[][] capacity, int s, int t) {
		HashSet<Integer> visited = new HashSet<Integer>();
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		q.add(s);
		visited.add(s);
		while (!q.isEmpty()) {
			int u = q.poll();
			for (int v = 0; v < capacity.length; v++) {
				if (!visited.contains(v) && capacity[u][v] > 0) {
					visited.add(v);
					q.addLast(v);
				}
			}
		}
		return visited;
	}
	private static void edmundsKarp(int[][] capacity, int s, int t) {
		while (true) {
			int [] pred = new int[capacity.length];
			int m = bfs(capacity, s, t, pred);
			if (m == 0)
				break;
			int v = t;
			while (v != s) {
				int u = pred[v];
				capacity[u][v] = capacity[u][v] - m;
				capacity[v][u] = capacity[v][u] - m;
				v = u;
			}
		}
	}

	private static int bfs(int[][] capacity, int s, int t, int[] pred) {
		HashSet<Integer> visited = new HashSet<Integer>();
		visited.add(s);
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		int [] M = new int[capacity.length];
		M[s] = Integer.MAX_VALUE;
		q.addLast(s);
		while (!q.isEmpty()) {
			int u = q.poll();
			for (int v = 0; v < capacity.length; v++) {
				if (capacity[u][v] > 0 && !visited.contains(v)) {
					pred[v] = u;
					M[v] = Math.min(M[u], capacity[u][v]);
					if (v != t) {
						q.addLast(v);
						visited.add(v);
					} else {
						return M[t];
					}
				}
			}
		}
		return 0;
	}
}
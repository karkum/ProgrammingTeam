import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
Passes on icpc site
@author karthik
*/
public class UndergroundCables {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextInt()) {
			int N = scan.nextInt();
			if (N == 0)
				break;
			Edge[] edges = new Edge[(N*(N-1))/2];
			ArrayList<Point> points = new ArrayList<Point>();
			for (int i = 0; i < N; i++) {
				points.add(new Point(scan.nextInt(), scan.nextInt()));
			}
			int count = 0;
			for (int i = 0; i < N; i++) {
				for (int j = i+1; j < N; j++) {
					if(i!=j)
						edges[count++] = new Edge(i, j, points.get(i).distance(points.get(j)));
				}
			}
			kruskals(edges, N);
		}

	}

	private static void kruskals(Edge[] edges, int total) {
		Arrays.sort(edges);
		int [] arr = new int[total];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		double totalMinLength = 0.0;
		for (int i = 0; i < edges.length; i++) {
			Edge thisOne = edges[i];
			int f = thisOne.from;
			int t = thisOne.to;
			double weight = thisOne.weight;
			if (arr[f] != arr[t]) {
				totalMinLength += weight;
				int temp = arr[f];
				for (int j = 0; j < arr.length; j++) {
					if (arr[j] == temp)
						arr[j] = arr[t];
				}
			} 

		}
			System.out.printf("%.2f%n", totalMinLength);

	}


	private static class Edge implements Comparable <Edge>{
		private int from;
		private int to;
		private double weight;

		public Edge(int f, int t, double weight) {
			from = f;
			to = t;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge arg0) {
			return Double.compare(weight, arg0.weight);
		}
	}
}

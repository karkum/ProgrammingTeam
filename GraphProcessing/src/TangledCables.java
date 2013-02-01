import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
Passes on ICPC site
@author karthik kumar
*/
public class TangledCables {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextDouble()) {
			double cableLength = scan.nextDouble();
			int numOfHouses = scan.nextInt();
			ArrayList<String> houses = new ArrayList<String>();
			for (int i = 0; i < numOfHouses; i++) {
				houses.add(scan.next());
			}
			int numOfConnections = scan.nextInt();
			Edge [] edges = new Edge[numOfConnections];

			for (int i = 0; i < numOfConnections; i++) {
				String from = scan.next();
				String to = scan.next();
				edges[i] = (new Edge(houses.indexOf(from), houses.indexOf(to), scan
						.nextDouble()));
			}
			kruskals(edges, cableLength, numOfHouses);
		}

	}

	private static void kruskals(Edge[] edges, double length, int total) {
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
		if (totalMinLength <= length) {
			System.out.printf("Need %.1f miles of cable%n", totalMinLength);
		} else {
			System.out.printf("Not enough cable%n");

		}
	}


	private static class Edge implements Comparable <Edge>{
		private int from;
		private int to;
		private double weight;

		public Edge(int f, int t, double w) {
			from = f;
			to = t;
			weight = w;
		}

		@Override
		public int compareTo(Edge arg0) {
			return Double.compare(weight, arg0.weight);
		}
	}
}

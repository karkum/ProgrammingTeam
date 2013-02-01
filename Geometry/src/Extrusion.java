import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Scanner;


public class Extrusion {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int N = sc.nextInt();
			if (N == 0) 
				break;
			Point2D.Double[] points = new Point2D.Double[N];
			for (int i = 0; i < N; i++) {
				points[i] = new Point2D.Double(sc.nextDouble(), sc.nextDouble());
			}
			double volume = sc.nextDouble();
			double sa = solve(points);
			System.out.printf("BAR LENGTH: %.2f%n", volume/sa);
		}
	}

	private static double solve(Double[] p) {
		double area = 0.0;
		int n = p.length;
		for (int i = 0; i < n; i++) {
		area += p[i].x * p[(i+1) % n].y - p[i].y * p[(i+1) % n].x;
		}
		return Math.abs(area/2.0);

	}
}

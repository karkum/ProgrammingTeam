import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.sound.sampled.Line;

public class DoorsandPenguins {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int cas = 0;
		outer: while (true) {
			int D = s.nextInt();
			int P = s.nextInt();
			if (D == 0 && P == 0)
				break;
			Point[] doors = new Point[D*4];
			Point[] penguins = new Point[P*4];
			for (int i = 0; i < D*4; i+=4) {
				int x1 = s.nextInt();
				int y1 = s.nextInt();
				int x2 = s.nextInt();
				int y2 = s.nextInt();
				doors[i] = new Point(x1, y1);
				doors[i+1] = new Point(x2, y2);
				doors[i+2] = new Point(x1, y2);
				doors[i+3] = new Point(x2, y1);
			}
			for (int i = 0; i < P*4; i+=4) {
				int x1 = s.nextInt();
				int y1 = s.nextInt();
				int x2 = s.nextInt();
				int y2 = s.nextInt();
				penguins[i] = new Point(x1, y1);
				penguins[i+1] = new Point(x2, y2);
				penguins[i+2] = new Point(x1, y2);
				penguins[i+3] = new Point(x2, y1);
			}
			ArrayList<Point> doorsconvex = new ConvexHull(doors).getResult();
			ArrayList<Point> pengsconvex = new ConvexHull(penguins).getResult();
			
			ArrayList<Line2D.Double> parallelLines = new ArrayList<Line2D.Double>();
			for (int i = 0; i < doorsconvex.size() - 1; i++) {
				Point a = doorsconvex.get(i);
				Point b = doorsconvex.get(i + 1);
				parallelLines.add(new Line2D.Double(a.x, a.y, b.x, b.y));
			}
			Point a = doorsconvex.get(0);
			Point b = doorsconvex.get(doorsconvex.size() - 1);
			parallelLines.add(new Line2D.Double(a.x, a.y, b.x, b.y));
			
			for (int i = 0; i < parallelLines.size(); i++) {
				Line2D.Double l = parallelLines.get(i);
				double x1 = l.x1;
				double y1 = l.y1;
				double x2 = l.x2;
				double y2 = l.y2;
				double A = y2 - y1;
				double B = x2 - x1;
				double C = x1*y2 - x2*y1;
				int arr[] = new int[pengsconvex.size()];
				int k = 0;
				for (Point p : pengsconvex) {
					int x = p.x;
					int y = p.y;
					double newC = A*x + B*y;
					if (newC < 0) {
						arr[k++] = -1;
					} else if (newC > 0) {
						arr[k++] = 1;
					} else {
						arr[k++] = 0;
					}
				}
				int []ones = new int[arr.length];
				Arrays.fill(ones, 1);
				int []negones = new																								 int[arr.length];
				Arrays.fill(negones, -1);
				if (Arrays.equals(ones, arr) || Arrays.equals(negones, arr)) {
					Line2D.Double otherPoint = parallelLines.get((i+1)%parallelLines.size());
					double px = otherPoint.x1;
					double py = otherPoint.y1;
					double dC = A*px+B*py;
					Point x = pengsconvex.get(0);
					double pC = A*x.x+B*x.y;
					if (pC > 0 && dC < 0 || pC < 0 && dC > 0) {
						System.out.print("Case " + ++cas+ ": It is possible to separate the two groups of vendors.\n\n");
						continue outer;
					} else {
						continue;
					}
					
				} else {
					continue;
				}
				
			}		
			
			
			System.out.print("Case " + ++cas + ": It is not possible to separate the two groups of vendors.\n\n");
		}
	}

	static double det(double x1, double y1, double x2, double y2) {
		return x1 * y2 - y1 * x2;
	}

	static Point2D.Double intersects(Point2D.Double p1, Point2D.Double p2,
			Point2D.Double p3, Point2D.Double p4) {
		double d = det(p1.x - p2.x, p1.y - p2.y, p3.x - p4.x, p3.y - p4.y);
		double x12 = det(p1.x, p1.y, p2.x, p2.y);
		double x34 = det(p3.x, p3.y, p4.x, p4.y);
		// assert d != 0 (lines are known to intersect and are not at right
		// angle)
		double x = det(x12, p1.x - p2.x, x34, p3.x - p4.x) / d;
		double y = det(x12, p1.y - p2.y, x34, p3.y - p4.y) / d;
		return new Point2D.Double(x, y);

	}

}

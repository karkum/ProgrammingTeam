import java.awt.geom.Point2D;
import java.util.Scanner;

public class LineOfSight {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			double w1 = scan.nextDouble();
			double h1 = scan.nextDouble();
			double w2 = scan.nextDouble();
			double h2 = scan.nextDouble();
			double x = scan.nextDouble();
			double y = scan.nextDouble();
			// line 1 info
			if (w1 == 0 && h1 == 0 && w2 == 0 && h2 == 0 && x == 0 && y == 0)
				break;
			Point2D.Double line1p1 = new Point2D.Double(0, 0);
			Point2D.Double line1p2 = new Point2D.Double(x - (w2 / 2), y
					+ (h2 / 2));

			// line2 info
			Point2D.Double line2p1 = new Point2D.Double(w1, 0);
			Point2D.Double line2p2 = new Point2D.Double(x + (w2 / 2), y
					+ (h2 / 2));

			// upper line
			Point2D.Double lineupp1 = new Point2D.Double(0, h1);
			Point2D.Double lineupp2 = new Point2D.Double(w1, h1);
			double floorArea = (w1 * h1) - (w2 * h2);

			//intersection of line1 and upper line
			Point2D.Double intersection1 = intersects(line1p1, line1p2,
					lineupp1, lineupp2);

			//probably not need the next two cases, its when the middle box thing is pushed all the way to either side of the floor
			if (intersection1 == null) {
				Point2D.Double p[] = new Point2D.Double[3];
				p[0] = line2p2;
				double m = (line2p2.y-line2p1.y)/(line2p2.x-line2p1.x);
				double b = line2p2.y-(m*line2p2.x);
				double ypoint = b;
				p[1] = new Point2D.Double(0, ypoint);
				p[2] = new Point2D.Double(0, line2p2.y);;
				System.out.printf("%.1f%%\n",
						(triangle_area(p[0], p[1], p[2]) / floorArea) * 100);
				continue;
			}
			Point2D.Double intersection2 = intersects(line2p1, line2p2,
					lineupp1, lineupp2);
			if (intersection2 == null) {
				Point2D.Double p[] = new Point2D.Double[3];
				p[0] = line1p2;
				double m = (line1p2.y-line1p1.y)/(line1p2.x-line1p1.x);
				double b = line1p2.y-(m*line1p2.x);
				double ypoint = m*(w1)+b;
				p[1] = new Point2D.Double(w1, ypoint);
				p[2] = new Point2D.Double(w1, line1p2.y);
				System.out.printf("%.1f%%\n",
						(triangle_area(p[0], p[1], p[2]) / floorArea) * 100);
				continue;
			}

			if (Double.compare(intersection1.x, intersection2.x) > 0) {
				Point2D.Double intersection = intersects(line1p1, line1p2,
						line2p1, line2p2);
				double triarea = triangle_area(line1p2, line2p2, intersection);
				System.out.printf("%.1f%%\n", (triarea / floorArea) * 100);
			} else if (Double.compare(intersection1.x, intersection2.x) == 0) {
				double triarea = triangle_area(line1p2, line2p2, intersection1);
				System.out.printf("%.1f%%\n", (triarea / floorArea) * 100);
			} else if (Double.compare(intersection1.x, intersection2.x) < 0) {
				Point2D.Double p[] = new Point2D.Double[4];
				p[0] = line1p2;
				p[1] = line2p2;
				p[2] = intersection2;
				p[3] = intersection1;
				System.out.printf("%.1f%%\n",
						(areaPolygon(p) / floorArea) * 100);
			}
		}
	}

	static double areaPolygon(Point2D.Double p[]) {
		double area = 0.0;
		int n = p.length;
		for (int i = 0; i < n; i++) {
			area += p[i].x * p[(i + 1) % n].y - p[i].y * p[(i + 1) % n].x;
		}
		return Math.abs(area / 2.0);
	}

	static double triangle_area(Point2D.Double a, Point2D.Double b,
			Point2D.Double c) {
		return Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x
				* (a.y - b.y)) / 2.0;
	}

	static double det(double x1, double y1, double x2, double y2) {
		return x1 * y2 - y1 * x2;
	}

	static Point2D.Double intersects(Point2D.Double p1, Point2D.Double p2,
			Point2D.Double p3, Point2D.Double p4) {
		double m1 = (p2.y-p1.y)/(p2.x-p1.x);
		if (p2.x-p1.x == 0) {
			//infinite slope
			return null;
		}
		double b1 = p2.y - (m1*p2.x);
		double m2 = (p4.y-p3.y)/(p4.x-p3.x);
		if (p4.x-p3.x == 0) {
			//infinite slope
			return null;
		}
		double b2 = p4.y - (m2*p4.x);
		double x = (b1-b2)/(m2-m1);
		double y = m1*x+b1;
		return new Point2D.Double(x, y);
	}

}

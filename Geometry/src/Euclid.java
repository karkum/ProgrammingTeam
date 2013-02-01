import java.awt.geom.Point2D;
import java.util.Scanner;

public class Euclid {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			double xa = scan.nextDouble();
			double ya = scan.nextDouble();
			double xb = scan.nextDouble();
			double yb = scan.nextDouble();
			double xc = scan.nextDouble();
			double yc = scan.nextDouble();
			double xd = scan.nextDouble();
			double yd = scan.nextDouble();
			double xe = scan.nextDouble();
			double ye = scan.nextDouble();
			double xf = scan.nextDouble();
			double yf = scan.nextDouble();
			if (xa == 0 && ya == 0 && xb == 0 && yb == 0 && xc == 0 && yc == 0
					&& xd == 0 && yd == 0 && xe == 0 && ye == 0 && xf == 0
					&& yf == 0)
				break;
			Point2D.Double a = new Point2D.Double(xa, ya);
			Point2D.Double b = new Point2D.Double(xb, yb);
			Point2D.Double c = new Point2D.Double(xc, yc);
			Point2D.Double d = new Point2D.Double(xd, yd);
			Point2D.Double e = new Point2D.Double(xe, ye);
			Point2D.Double f = new Point2D.Double(xf, yf);

			double triangearea = triangleArea(d, e, f);
			double w = Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x)
					* (b.x - a.x));
			Point2D.Double vectorAB = new Point2D.Double(b.x - a.x, b.y - a.y);
			Point2D.Double vectorAC = new Point2D.Double(c.x - a.x, c.y - a.y);
			double magab = (Math.sqrt((vectorAB.x * vectorAB.x)
					+ (vectorAB.y * vectorAB.y)));
			double magac = (Math.sqrt((vectorAC.x * vectorAC.x)
					+ (vectorAC.y * vectorAC.y)));
			double angle = Math.acos((vectorAB.x * vectorAC.x + vectorAB.y
					* vectorAC.y)
					/ (magab * magac));
			double l = triangearea / (Math.sin(angle) * w);
			double m = (c.y - a.y) / (c.x - a.x);
			double hx = 0.0;
			double hy = 0.0;
			double bforthis = a.y-m*a.x;
			
			if (a.x > c.x) {
				hx = a.x - l/(Math.sqrt(1+(m*m)));
				hy = (m*hx) +(a.y-(m*a.x));
			}
			else if (a.x < c.x) {
				hx = a.x + l/(Math.sqrt(1+(m*m)));
				hy = (m*hx) +(a.y-(m*a.x));
			}
			else {
				hx = a.x;
				if (c.y < a.y)
					hy = a.y - l;
				else if (c.y > a.y)
					hy = a.y +l;
			}
			
			double m1 = (b.y-a.y)/(b.x-a.x);
			double m2 = m;
			
			double b2 = b.y - b.x *m2;
			double b1 = hy-m1*hx;
			double gx = 0.0;
			double gy = 0.0;
			if (b.x == a.x) {
				gx = hx;
				gy = m2 * gx + b2;
			} else if (a.x == c.x) {
				gx = b.x;
				gy = m1 * gx + b1;
			} else {
				gx = (b1 - b2)/(m2-m1);
				gy = m2*gx + b2;
			}
			
			System.out.printf("%.3f %.3f %.3f %.3f%n", gx, gy, hx, hy);
		}

	}

	private static double triangleArea(Point2D.Double a, Point2D.Double b,
			Point2D.Double c) {
		return Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x
				* (a.y - b.y)) / 2.0;
	}
}

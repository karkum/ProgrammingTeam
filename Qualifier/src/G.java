import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Scanner;

public class G {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String> targets = new ArrayList<String>();
		ArrayList<Point> shots = new ArrayList<Point>();
		while (sc.hasNext()) {
			int numtargets = sc.nextInt();
			sc.nextLine();
			for (int i = 0; i < numtargets; i++) {
				targets.add(sc.nextLine());
			}
			int numshots = sc.nextInt();
			for (int i = 0; i < numshots; i++) {
				shots.add(new Point(sc.nextInt(), sc.nextInt()));
			}
		}
		solve(targets, shots);
	}

	public static void solve(ArrayList<String> targets, ArrayList<Point> shots) {
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
		ArrayList<Circle> circles = new ArrayList<Circle>();
		for (String str : targets) {
			String [] s = str.split(" ");
			if (s[0].equals("rectangle")){
				int x1 = Integer.valueOf(s[1]);
				int x2 = Integer.valueOf(s[2]);
				int y1 = Integer.valueOf(s[3]);
				int y2 = Integer.valueOf(s[4]);
				rects.add(new Rectangle(x1, y2, Math.abs(y1- x1), Math.abs(y2 -x2)));
//				rects.add(new Rectangle(x1, y2,Math.abs(y2 -x2), ));

			} else {
				int x = Integer.valueOf(s[1]);
				int y = Integer.valueOf(s[2]);
				int r = Integer.valueOf(s[3]);
				circles.add(new Circle(x, y, r));
			}
		}
		
		for (Point p : shots) {
			int count = 0;
			for (Rectangle r : rects) {

				if (has(r, p))
					count++;
			}
			for (Circle c : circles) {
				if (c.isInside(p.x, p.y))
					count++;
			}
			System.out.println(count);
		}
	}
	private static boolean has(Rectangle r, Point p) {
		int px = p.x;
		int py = p.y;
		Point topLeft = new Point(r.x, r.y);
		int w = (int)r.getWidth();
		int h = (int)r.getHeight();
		if (px >= topLeft.x && px <= topLeft.x + w)
			if (py >= topLeft.y -h && py <= topLeft.y)
				return true;
		return false;
	}
	private static boolean rectOnBounds(Rectangle r, Point p) {
		int px = p.x;
		int py = p.y;
		Point topLeft = new Point(r.x, r.y);
		int w = (int)r.getWidth();
		int h = (int)r.getHeight();
		if (px == topLeft.x || px == topLeft.x + w)
			if (px == topLeft.y || py == topLeft.y -h)
				return true;
		
		
		return false;
	}

	private static class Circle {
		int x;
		int y;
		int rad;

		public Circle(int x, int y, int rad) {
			this.x = x;
			this.y = y;
			this.rad = rad;
		}

		public boolean isInside(int otherx, int othery) {
			return Math.pow(otherx - x, 2) + Math.pow(othery - y, 2) <= Math.pow(rad, 2);
		}
	}
}

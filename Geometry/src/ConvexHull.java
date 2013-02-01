import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ConvexHull {
	ArrayList<Point> hull;
	public ConvexHull(Point[] points) {
		hull = new ArrayList<Point>();
		Arrays.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point arg0, Point arg1) {
				Point a = (Point) arg0;
				Point b = (Point) arg1;
				if (a.y == b.y) {
					return Integer.valueOf(a.x).compareTo(Integer.valueOf(b.x));
				} else {
					return Integer.valueOf(a.y).compareTo(Integer.valueOf(b.y));
				}
			}
		});

		final Point firstPoint = points[0];
		Arrays.sort(points, 1, points.length, new Comparator<Point>() {

			@Override
			public int compare(Point p1, Point p2) {
				return -1*ccw(firstPoint, p1, p2);
			}

		});

		hull.add(points[0]);
		int N = points.length;
		hull.add(points[1]);
		for (int i = 2; i < N; i++) {
			int ccw = ccw(hull.get(hull.size() - 2), hull.get(hull.size() - 1), points[i]);
			//right turn
			if (ccw < 0)
			{
				hull.remove(hull.size() - 1);
				i--;
			}
			//on the same line
			else if (ccw == 0)
			{
				hull.remove(hull.size() - 1);
				hull.add(points[i]);
			}
			//left turn
			else
				hull.add(points[i]);

		}
//		System.out.println(hull.toString());

	}
	public ArrayList<Point> getResult() {
		return hull;
	}
	static int ccw(Point p1, Point p2, Point p3) {	
		return ((p2.x - p1.x)*(p3.y - p1.y) - (p2.y - p1.y)*(p3.x - p1.x))*-1;
	}
}

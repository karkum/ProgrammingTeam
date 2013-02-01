import java.awt.Point;
import java.util.Scanner;

/**
 * Passes test input and on the submission site. Doesn't use Edge class, 
 * instead use a Vertex array that represents everything we need.
 * 
 * @author karthik
 * 
 */
public class H3 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numVert = scan.nextInt();
		Vertex[]everything = new Vertex[numVert + 2];
		// Read vertices input into a list of vertices
		for (int i = 0; i < numVert; i++) {
			scan.nextLine();
			everything[i + 1] = new Vertex(i+1, new Point(scan.nextInt(), scan.nextInt()), false, Integer.MAX_VALUE); 
			//			vertices.add(new Vertex(i + 1, new Point(scan.nextInt(), scan.nextInt())));
		}
		// Read in start and end points
		Vertex start = new Vertex(0, new Point(scan.nextInt(), scan.nextInt()));
		scan.nextLine();
		Vertex end = new Vertex(numVert + 1, new Point(scan.nextInt(), scan.nextInt()));
//		vertices.add(0, start);
//		vertices.add(numVert + 1, end);
		everything[0] = start;
		everything[numVert+1] = end;
		solve(everything, start, end);
	}

	private static void solve(Vertex [] everything, Vertex start, Vertex end) {
		// store which vertices we have seen
		// First vertex's distance is 0
		everything[start.id].distance = 0;
		while (true) {
			Vertex v = findMin(everything);
			if (v.equals(end))
				break;
			if (everything[v.id].visited)
				continue;
			everything[v.id].visited = true;
			for (int i = 0; i < everything.length; i++) {
				Vertex w = everything[i];
				if (!v.equals(w)) {
					int distance = w.getDistance(v);
					if (everything[w.id].distance > everything[v.id].distance + distance) {
						everything[w.id].distance = everything[v.id].distance + distance;
//						w.setPred(v);
						everything[i].pred = v;
					}
				}
			}
		}
		// find and print out the path
		findPath(everything, end);
	}

	private static Vertex findMin(Vertex [] arr) {
		Vertex min = new Vertex(Integer.MAX_VALUE);
		for (Vertex v: arr) {
			if (!v.visited && v.distance < min.distance) {
				min = v;
			}
		}
		return min;
	}
	private static void findPath(Vertex [] arr, Vertex end) {
		StringBuilder string = new StringBuilder();
		Vertex target = arr[arr.length - 1];
		if (target != null) {
			while (target != null) {
				// the mapping is used for first vertex =1, so subtract 1 to get
				// expected output
				if (target.id != end.id && target.id - 1 != -1)
					string.insert(0, target.id - 1 + "\n");
				if (target.pred != null)
					target = arr[target.pred.id];
				else 
					break;
			}
		}
		if (string.length() == 0)
			System.out.println("-");
		else
			System.out.println(string.toString());
	}

	private static class Vertex {
		int id;
		Point p;
		Vertex pred;
		boolean visited;
		int distance;

		public Vertex(int id, Point p) {
			this.id = id;
			this.p = p;
		}

		public Vertex(int id, Point p, boolean vis, int dist) {
			this.id = id;
			this.p = p;
			this.visited = vis;
			this.distance = dist;
			pred = new Vertex(Integer.MAX_VALUE);
		}

		public Vertex(int maxValue) {
			distance = maxValue;
		}

		public void setPred(Vertex pr) {
			pred = pr;
		}

		public int getDistance(Vertex dest) {
			Point o = dest.p;
			return (int) (o.distance(p) * o.distance(p));
		}

		public String toString() {
			return id + "@[" + p.x + ", " + p.y + "] " + visited + " dist: " + distance + " pred: " + pred.id;
		}

		@Override
		public boolean equals(Object _other) {
			Vertex other = (Vertex) _other;
			return id == other.id && p.x == other.p.x && p.y == other.p.y;
		}
	}
}

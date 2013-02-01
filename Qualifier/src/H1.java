import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Passes test input. Times out on submission site.
 * @author karthik
 *
 */
public class H1 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numVert = scan.nextInt();
		//Read vertices input into a list of vertices
		List<Vertex> vertices = new ArrayList<Vertex>();
		for (int i = 0; i < numVert; i++) {
			scan.nextLine();
			vertices.add(new Vertex(i + 1, scan.nextInt(), scan.nextInt()));
		}
		//Read in start and end points
		Vertex start = new Vertex(0, scan.nextInt(), scan.nextInt());
		scan.nextLine();
		Vertex end = new Vertex(numVert + 1, scan.nextInt(), scan.nextInt());
		vertices.add(0, start);
		vertices.add(numVert + 1, end);

		solve(vertices, start, end);
	}

	private static void solve(List<Vertex> vertices, Vertex start, Vertex end) {
		int numOfVertices = vertices.size();
		//store which vertices we have seen
		boolean[] marked = new boolean[numOfVertices];
		//store the distances for each vertex
		int[] dist = new int[numOfVertices];
		//initially set all distances to infinity
		for (int v = 0; v < numOfVertices; v++) {
			dist[v] = Integer.MAX_VALUE;
		}
		//First vertex's distance is 0
		dist[start.id] = 0;
		//Use a tree map to store the distance with a vertex
		PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
		//Add the starting vertex to our queue
		queue.add(new Edge(start, start));	
		while (!queue.isEmpty()) {
			Vertex v = queue.poll().destination;
			if (v.equals(end))
				break;
			if (marked[v.id])
				continue;
			marked[v.id] = true;
			for (int i = 0; i < vertices.size() ; i++) {
				Vertex w = vertices.get(i);
				if (!v.equals(w)) {
					int distance = w.getDistance(v);
					if (dist[w.id] > dist[v.id] + distance) {
						dist[w.id] = dist[v.id] + distance;
						w.setPred(v);
						queue.offer(new Edge(w, dist[w.id]));
					}
				}
			}
		}
		//find and print out the path 
		findPath(end);
	}

	private static void findPath(Vertex end) {
		StringBuilder string = new StringBuilder();
		Vertex target = end;
		if (target != null) {
			while (target != null) {
				//the mapping is used for first vertex =1, so subtract 1 to get expected output
//				if (target.id != end.id && target.id- 1 != -1)
				if (target.id != end.id)
					string.insert(0, target.id - 1 + "\n");
				target = target.pred;
			}
		}
		if (string.length() == 0)
			System.out.println("-");
		else
			System.out.println(string.toString());
	}

	private static class Vertex {
		int id;
		int x, y;
		Vertex pred;

		public Vertex(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		public void setPred(Vertex pr) {
			pred  = pr;
		}

		public int getDistance(Vertex dest) {
			int sx = x;
			int sy = y;
			int dx = dest.x;
			int dy = dest.y;
			return (sx - dx) * (sx - dx) + (sy - dy) * (sy - dy);
		}

		public String toString() {
			return id + ":[" + x + ", " + y + "]";
		}

		@Override
		public boolean equals(Object _other) {
			Vertex other = (Vertex) _other;
			return id == other.id && x == other.x && y == other.y;
		}
	}

	private static class Edge implements Comparable<Edge> {
		Vertex source;
		Vertex destination;
		int weight;
		
		public Edge(Vertex d, int w) {
			this.destination = d;
			weight = w;
		}
		public Edge(Vertex s, Vertex dest) {
			source = s;
			destination = dest;
			int sx = s.x;
			int sy = s.y;
			int dx = dest.x;
			int dy = dest.y;
			weight = (sx - dx) * (sx - dx) + (sy - dy) * (sy - dy);
		}

		public String toString() {
			return source.toString() + " to " + destination.toString()
					+ " with weight " + weight;
		}
		@Override 
		public int compareTo(Edge o) {
			return Integer.valueOf(weight).compareTo(Integer.valueOf(o.weight));
		}
		@Override
		public boolean equals(Object ot) {
			Edge e = (Edge)ot;
			return destination.id == e.destination.id;
		}
		@Override
		public int hashCode() {
			return destination.id;
		}
	}
}

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

/**
 * Passes ICPC online judge.
 * 
 * @author karthik
 * 
 */
public class SignalStrength {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int cas = 0;
		while (true) {
			List<Node> nodes = new ArrayList<Node>();
			int numSwitches = Integer.valueOf(scan.next());
			if (numSwitches <= 0) {
				break;
			}
			for (int i = 0; i < numSwitches; i++) {
				double thisMultiplier = Double.valueOf(scan.next());
				int numConnected = Integer.valueOf(scan.next());
				int[] swi = new int[numConnected];
				double[] multi = new double[numConnected];
				for (int j = 0; j < numConnected; j++) {
					swi[j] = Integer.valueOf(scan.next());
					multi[j] = Double.valueOf(scan.next());
				}
				Node node = new Node(i, thisMultiplier, swi, multi, false);
				nodes.add(node);
			}
			solve(nodes, numSwitches, cas++);
		}
	}

	private static void solve(List<Node> nodes, int numSwitches, int cas) {
		Deque<Node> bfs = new ArrayDeque<Node>();
		Node start = nodes.get(0);
		start.output = 1.0 * start.thisMultiplier;
		bfs.offer(start);
		while (!bfs.isEmpty()) {
			Node node = bfs.poll();
			//go through all connected switches
			for (int i = 0; i < node.lines.length; i++) {
				int connectedIndex = node.switches[i];
				double newSignal = node.output * node.lines[i]
						* nodes.get(connectedIndex).thisMultiplier;
				//we found a better output path, replace it in the list
				if (newSignal > nodes.get(connectedIndex).output) {
					Node newNode = nodes.get(connectedIndex);
					newNode.output = newSignal;
					nodes.set(connectedIndex, newNode);
				}
				if (!nodes.get(connectedIndex).visited)
					bfs.add(nodes.get(connectedIndex));
			}
			// a node is done when we have gone through all its edges
			node.visited = true;
		}
		DecimalFormat decim = new DecimalFormat("0.00");
		System.out.println("Network " + (cas + 1) + ": "
				+ decim.format(nodes.get(numSwitches - 1).output));
	}

	private static class Node {
		@SuppressWarnings("unused")
		int id;
		double thisMultiplier;
		int[] switches;
		double[] lines;
		boolean visited;
		double output;

		public Node(int id, double mult, int[] swi, double[] multi, boolean vis) {
			this.id = id;
			this.visited = vis;
			this.thisMultiplier = mult;
			this.switches = swi;
			this.lines = multi;
			output = Double.MIN_VALUE;
		}
	}
}
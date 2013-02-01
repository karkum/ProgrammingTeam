import java.util.*;
/**
 * Based on the state space exploration sample posted.
 * Submitted to ICPC judge, completes in 2.54 seconds.
 * 
 * Karthik Kumar
 * 
 * Based on State space exploration sample posted on class site
 */
public class Marbles {
	public static void main(String []av) {
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()) {
			int n1 = scan.nextInt();
			int n2 = scan.nextInt();
			int n3 = scan.nextInt();

			State state = new State(n1, n2, n3);
			if ((n1 + n2 + n3)%3 == 0 ) {
				if(!solve(state)) {
					System.out.printf("%4d%4d%4d\n", n1, n2, n3);
				}
			} else {
				System.out.printf("%4d%4d%4d\n", n1, n2, n3);
			}
			System.out.println("============");
		}
	}

	static class State {
		int n1, n2, n3;
		State(int n1, int n2, int n3) { 
			this.n1 = n1; 
			this.n2 = n2;
			this.n3 = n3;
		}

		@Override 
		public String toString() {
			return this.n1 + " " + this.n2 + " " + this.n3;
		}

		@Override
		public boolean equals(Object _that) {
			State that = (State)_that;
			return (this.n1==that.n1) && (this.n2==that.n2) & (this.n3==that.n3);
		}

		@Override
		public int hashCode() {
			//			String s = "" + n1 + n2 + n3;
			//			return (s).hashCode();
			return n1 ^ n2 ^ n3;
		}

		List<State> successors() {  // possible moves
			List<State> M = new ArrayList<State>();
			//compare the first two
			if (n1 <= n2) {
				M.add(new State(n1 + n1, n2 - n1, n3));
			} else {
				M.add(new State(n1 - n2, n2 + n2, n3));
			}
			if (n1 <= n3) {
				M.add(new State(n1 + n1, n2, n3 - n1));
			} else {
				M.add(new State(n1 - n3, n2, n3 + n3));
			}
			if (n2 <= n3) {
				M.add(new State(n1, n2 + n2, n3 - n2));
			} else {
				M.add(new State(n1, n2 - n3, n3 + n3));
			}
			return M;
		}

		boolean isfinal() {
			return (n1 == n2) && n2 == n3;
		}
	}

	static boolean solve(State start) {
		Set<State> visited = new HashSet<State>();                  // has this state been visited?
		Map<State, State> pred = new HashMap<State, State>();       // predecessor on the shortest path to the start state
		//		Map<State, Integer> dist = new HashMap<State, Integer>();   // shortest distance to start state
		Deque<State> bfs = new ArrayDeque<State>();                 // BFS queue
		bfs.offer(start);
		//		dist.put(start, 0);

		while (bfs.size() > 0) {
			State s = bfs.poll();
			//			int n = dist.get(s);
			visited.add(s);

			for (State succ : s.successors()) {
				if (visited.contains(succ))
					continue;

				if (!pred.containsKey(succ))
					pred.put(succ, s);
				else 
					continue;

				if (succ.isfinal()) {
					output(succ, pred);
					return true;
				}

				//				if (!dist.containsKey(succ)) {
				//					dist.put(succ, n+1);
				//					bfs.offer(succ);
				//				}
				bfs.offer(succ);
			}
		}
		return false;
	}

	/* Compute and output path */
	static void output(State finalState, Map<State, State> pred) {
		List<State> revPath = new ArrayList<State>();
		State s = finalState;
		while (pred.containsKey(s)) {
			revPath.add(s);
			s = pred.get(s);
		}
		revPath.add(s);

		for (int i = 0; i < revPath.size(); i++) {
			State st = revPath.get(revPath.size() - 1 - i);
			System.out.printf("%4d%4d%4d\n", st.n1, st.n2, st.n3);
		}
	}
}
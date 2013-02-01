import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Passes ICPC judge. Parses tree, looks for common suffixes.
 * @author karthik
 *
 */
public class TrieMain {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String line = sc.nextLine();
			if (line.equals("END"))
				break;
			
			new TrieSolver(line).solve();
		}
	}
	private static class TrieSolver{
	    private static Deque<Character> tokens = new ArrayDeque<Character>();
	    private HashMap<String, Integer> map = new HashMap<String, Integer>();
	    int max = -1;
	    private TrieNode maxTrieNode = null;
		public TrieSolver(String in) {
			for (Character c : in.toCharArray()) {
				tokens.add(c);
			}
			
		}
		
		public void solve() {
			TrieNode root = parse();
			findAnswer(root);
			System.out.printf("%s %d\n", maxTrieNode, max);
		}
		private void findAnswer(TrieNode root) {
			int thisSavings = 0;
			String start = root.toString();
			if (map.containsKey(start)) {
				//if we have seen this before, add to past savings
				thisSavings = map.get(start) + root.count();
			}
			map.put(start, thisSavings);
			if (thisSavings > max) {
				//better than what we have seen before
				max = thisSavings;
				maxTrieNode = root;
			} else {
				if (thisSavings == max) {
					if (maxTrieNode != null) {
						if (start.length() < maxTrieNode.toString().length()) {
							max = thisSavings;
							maxTrieNode = root;
						}
					}
				}
			}
			//recurse
			if (root.left.ch != '#')
				findAnswer(root.left);
			if (root.right.ch != '#')
				findAnswer(root.right);
		}

		private static TrieNode parse() {
			char ch = tokens.poll();
				if (ch == '#')
					return new TrieNode(ch, null, null);
			return new TrieNode(ch, parse(), parse());
		}
		private static class TrieNode {
			char ch;
			TrieNode left;
			TrieNode right;
			public TrieNode(char c, TrieNode l, TrieNode r) {
				ch = c;
				left = l;
				right = r;
			}
			public String toString() {
				if (ch == '#')
					return "#";
				return ""+ch + left.toString() + right.toString();
			}
			public int count() {
				if (ch == '#')
					return 0;
				return 1 + left.count() + right.count();
			}
		}
	}
}

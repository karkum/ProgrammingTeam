import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner;
/**
 * Karthik Kumar (kkumar91@vt.edu)
 * Passed ACM Online judge in .194 seconds.
 *
 */
public class MobileAlabama {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(System.in);
		//Get all the input for a certain case and pass it into solve
		StringBuilder buff = new StringBuilder();
		int lParenCount = 0;
		int rParenCount = 0;
		while (true) {
			String line = s.next();
			if (line.equals("\n"))
				continue;
			if (line.equals("()"))
				break;
			buff.append(line + " ");
			lParenCount += findLParen(line);
			rParenCount += findRParen(line);
			if (lParenCount == rParenCount) {
		
				new MobileSolver(buff.toString()).solve();
				buff = new StringBuilder();
				lParenCount = 0;
				rParenCount = 0;
			}
		}
	}

	private static int findLParen(String line) {
		int count = 0;
		for (char c : line.toCharArray())
			if (c == '(')
				count++;
		return count;
	}

	private static int findRParen(String line) {
		int count = 0;
		for (char c : line.toCharArray())
			if (c == ')')
				count++;
		return count;
	}

	/*
	 * Grammar:
	 * Mobile->Bar
	 * Bar->id L Bar|Dec Bar|Dec 
	 * Dec->weight 
	 *
	 */
	public static class MobileSolver {
		HashMap<Integer, Double> answer = new HashMap<Integer, Double>();
		final static String delim = "[\\p{javaWhitespace}()]+";

		Deque<String> tokens = new ArrayDeque<String>();

		MobileSolver(String equation) {
			Scanner tokenScanner = new Scanner(equation).useDelimiter(delim);
			while (tokenScanner.hasNext())
				tokens.add(tokenScanner.next());
		}

		char lookahead() {
			if (tokens.isEmpty())
				return 0;
			return tokens.peek().charAt(0);
		}

		static abstract class ASTNode {
			double getWeight() {
				throw new Error("computeValue not implemented");
			}

			double getLength() {
				throw new Error("computeValue not implemented");
			}
		}

		static class Mobile extends ASTNode {
			Bar bar;

			public Mobile(Bar bar) {
				this.bar = bar;
			}

			public void solve() {
				bar.getLength();
			}

		}

		static class Bar extends ASTNode {
			int id;
			double length;
			ASTNode barOrDec1;
			ASTNode barOrDec2;

			public Bar(int id, double length, ASTNode barOrDec1,
					ASTNode barOrDec2) {
				this.id = id;
				this.length = length;
				this.barOrDec1 = barOrDec1;
				this.barOrDec2 = barOrDec2;
			}

			@Override
			double getWeight() {
				return barOrDec1.getWeight() + barOrDec2.getWeight();
			}

			double getLength() {
				double l1 = (length * barOrDec1.getWeight())
						/ (this.getWeight());
				double l2 = length - l1;
				return Math.min(l1, l2);
			}
		}

		static class Dec extends ASTNode {
			double weight;

			public Dec(double w) {
				weight = w;
			}

			@Override
			double getWeight() {
				return weight;
			}
		}

		void solve() {
			Mobile root = parseMobile();
			parse(root.bar);
			for (Integer i : answer.keySet()) {
				System.out.printf("Bar %d must be tied %.1f from one end.\n",
						i, answer.get(i));
			}
		}

		public void parse(Bar bar) {
			if (bar != null) {
				if (bar.barOrDec1 instanceof Bar) {
					parse((Bar) bar.barOrDec1);
				}
				if (bar.barOrDec2 instanceof Bar) {
					parse((Bar) bar.barOrDec2);
				}
				answer.put(bar.id, bar.getLength());
			}
		};

		Mobile parseMobile() {
			Bar b = parseBar();
			return new Mobile(b);
		}

		Bar parseBar() {
			String letterb = tokens.poll();
			int id = Integer.valueOf(tokens.poll());
			double length = Double.valueOf(tokens.poll());
			char nextChar = lookahead();
			ASTNode first = null, second = null;
			if (nextChar == 'D') {
				first = parseDec();
			} else if (nextChar == 'B') {
				first = parseBar();
			}
			nextChar = lookahead();
			if (nextChar == 'D') {
				second = parseDec();
			} else if (nextChar == 'B') {
				second = parseBar();
			}
			return new Bar(id, length, first, second);
		}

		ASTNode parseDec() {
			String letterD = tokens.poll();
			double weight = Double.parseDouble(tokens.poll());
			return new Dec(weight);
		}
	}
}
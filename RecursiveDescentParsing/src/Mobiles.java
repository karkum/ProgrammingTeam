import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Passes ACM judge input
 * Karthik Kumar (kkumar91@vt.edu)
 * 
 */
public class Mobiles {
	public static void main(String[] args) throws FileNotFoundException {
//		 Scanner s = new Scanner(new File("in.txt"));
		Scanner s = new Scanner(System.in);
		// Get all the input for a certain case and pass it into solve
		ArrayList<String> input = new ArrayList<String>();
		while (true) {
			String line = s.nextLine();

			if (line.equals("-1")) {
				new MobileSolver(input).solve();
				break;
			}
			if (line.equals("0")) {
				new MobileSolver(input).solve();
				input.clear();
			} else
				input.add(line);
		}
	}

	public static class MobileSolver {
		ArrayList<String> input = new ArrayList<String>();

		MobileSolver(ArrayList<String> input) {
			this.input = input;
		}

		static abstract class ASTNode {
			int id;

			double getWeight() {
				throw new Error("getWeight not implemented");
			}
		}

		static class Mobile extends ASTNode {
			Bar bar;

			public Mobile(Bar bar) {
				this.bar = bar;
			}
		}

		static class Bar extends ASTNode {
			double length;
			int flag;
			ASTNode barOrDec1;
			ASTNode barOrDec2;

			public Bar(double length, ASTNode barOrDec1, ASTNode barOrDec2) {
				this.length = length;
				this.barOrDec1 = barOrDec1;
				this.barOrDec2 = barOrDec2;
			}

			double getWeight() {
				return barOrDec1.getWeight() + barOrDec2.getWeight();
			}

			// Returns 0 if X is on left, 1 if its on right, 2 if not here
			private int findX() {
				int oneFlag = -1, twoFlag = -1;
				if (barOrDec1 != null && barOrDec1 instanceof Dec) {
					if (barOrDec1.getWeight() == -1) {
						oneFlag = 0;
					} else {
						oneFlag = 2;
					}
				} else
					oneFlag = ((Bar) barOrDec1).findX();
				if (barOrDec2 != null && barOrDec2 instanceof Dec) {
					if (barOrDec2.getWeight() == -1) {
						twoFlag = 1;
					} else {
						twoFlag = 2;
					}
				} else {
					twoFlag = ((Bar) barOrDec2).findX();
				}
				if (oneFlag == 0 || oneFlag == 1) {
					flag = 0;
				} else if (twoFlag == 1 || twoFlag == 0) {
					flag = 1;
				} else {
					flag = 2;
				}
				return flag;
			}
		}

		static class Dec extends ASTNode {
			double weight;

			public Dec(double w, int id) {
				weight = w;
				this.id = id;
			}

			@Override
			double getWeight() {
				return weight;
			}
		}

		void solve() {
			boolean flag = false;
			for (String str : input) {
				if (str.contains("X"))
					flag = true;
			}
			if (!flag) {
				System.out.println("The mobile cannot be balanced.");
				return;
			}
			Mobile root = parseMobile();
			int ret = root.bar.findX();
			ASTNode split = null;
			// if x is on the left, get right weight and balance on the left
			if (ret == 0) {
				double rightWeight = root.bar.barOrDec2.getWeight();
				parse(rightWeight, root.bar.barOrDec1, split, root.bar);
			} else if (ret == 1) {
				double leftWeight = root.bar.barOrDec1.getWeight();
				parse(leftWeight, root.bar.barOrDec2, split, root.bar);
			}
		}

		public void parse(double weight, ASTNode root, ASTNode splitPoint,
				Bar realRoot) {
			if (root instanceof Bar) {
				if (((Bar) root).flag == 0) {
					parse(weight - ((Bar) root).barOrDec2.getWeight(),
							((Bar) root).barOrDec1, ((Bar) root).barOrDec2,
							realRoot);
				}
				if (((Bar) root).flag == 1) {
					parse(weight - ((Bar) root).barOrDec1.getWeight(),
							((Bar) root).barOrDec2, ((Bar) root).barOrDec1,
							realRoot);
				}
			}
			if (root instanceof Dec) {
				boolean isX = ((Dec) root).getWeight() < 0;
				if (isX) {
					if (weight <= 0 || weight != splitPoint.getWeight()) {
						System.out.printf("The mobile cannot be balanced.\n");
						return;
					} else if (weight > 0) {
						System.out.printf("Object %d must have weight %.2f\n",
								root.id, splitPoint.getWeight());
						if (willSwing(realRoot))
							System.out.println("The mobile will swing freely.");
						else
							System.out
									.println("The mobile will not swing freely.");
						return;
					}
				}
			}
		};

		public boolean willSwing(Bar root) {
			boolean flag = true;
			if (root.barOrDec1 instanceof Bar && root.barOrDec2 instanceof Bar) {
				flag = ((Bar) root.barOrDec1).length
						+ ((Bar) root.barOrDec2).length <= (root.length + 1);
			} else if (!(root.barOrDec1 instanceof Bar)
					&& root.barOrDec2 instanceof Bar)
				flag = willSwing((Bar) root.barOrDec2);
			else if (!(root.barOrDec2 instanceof Bar)
					&& root.barOrDec1 instanceof Bar)
				flag = willSwing((Bar) root.barOrDec1);
			return flag;
		}

		Mobile parseMobile() {
			for (String s : input) {
				if (s.contains("B")) {
					Bar b = parseBar(s);
					return new Mobile(b);
				}
			}
			return null;
		}

		Bar parseBar(String barLine) {
			Scanner sc = new Scanner(barLine);
			String num = sc.next();
			char letterB = sc.next().charAt(0);
			double length = Double.valueOf(sc.next());
			int bar1 = Integer.parseInt(sc.next());
			int bar2 = Integer.parseInt(sc.next());
			ASTNode first = null, second = null;
			String line1 = input.get(bar1 - 1);
			String line2 = input.get(bar2 - 1);
			if (line1.contains("" + 'B')) {
				first = parseBar(line1);
			}
			if (line2.contains("" + 'B')) {
				second = parseBar(line2);
			}
			if (line1.contains("" + 'D')) {
				first = parseDec(line1);
			}
			if (line2.contains("" + 'D')) {
				second = parseDec(line2);
			}
			return new Bar(length, first, second);
		}

		ASTNode parseDec(String decLine) {
			Scanner scn = new Scanner(decLine);
			int id = scn.nextInt();
			if (!decLine.contains("" + 'X')) {

				String letterD = scn.next();
				double weight = Double.parseDouble(scn.next());
				return new Dec(weight, id);
			} else {
				return new Dec(-1, id);
			}
		}
	}
}
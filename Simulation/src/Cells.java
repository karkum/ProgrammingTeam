import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Cells {
	public static void main(String[] av) {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			int num = scan.nextInt();
			if (num == 0) {
				break;
			} else {
				double center_x = scan.nextDouble();
				double center_y = scan.nextDouble();
				double center_radius = scan.nextDouble();
				Tower center = new Tower(center_x, center_y, center_radius);
				ArrayList<Tower> towers = new ArrayList<Tower>();
				for (int i = 0; i < num - 1; i++) {
					double x = scan.nextDouble();
					double y = scan.nextDouble();
					double radius = scan.nextDouble();
					Tower t = new Tower(x, y, radius);
					towers.add(t);
				}
				int tries = 0;
				int success = 0;
				for (int j = 0; j < 1000000; j++) {
					Random rand = new Random();
					double x;
					double y;
					x = rand.nextDouble() * (center_radius * 2)
							+ (center_x - center_radius);
					y = rand.nextDouble() * (center_radius * 2)
							+ (center_y - center_radius);
					if (center.isIn(x, y)) {
						for (Tower t : towers) {
							if (t.isIn(x, y)) {
								success++;
								break;
							}
						}
						tries++;
					}

				}
				System.out.printf("%.2f\n", (double) success / tries);
			}
		}

	}

	private static class Tower {
		private double x;
		private double y;
		private double rad;

		public Tower(double x, double y, double rad) {
			this.x = x;
			this.y = y;
			this.rad = rad;
		}

		private boolean isIn(double a, double b) {

			return Math.pow(a - x, 2) + Math.pow(b - y, 2) <= Math.pow(rad, 2);
		}
	}
}

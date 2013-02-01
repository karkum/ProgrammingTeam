import java.util.ArrayList;
import java.util.Scanner;


public class C {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			double b0 = sc.nextDouble();
			double b1 = sc.nextDouble();
			double b2 = sc.nextDouble();
			double b3 = sc.nextDouble();
			double t0 = sc.nextDouble();
			double t1 = sc.nextDouble();
			double t2 = sc.nextDouble();
			double t3 = sc.nextDouble();
//			ArrayList<Double> lower = quadraticForm(3*(b3), 2*b2, b1);
//			ArrayList<Double> upper = quadraticForm(3*(t3), 2*t2, t1);
//			double uppest = Double.MIN_VALUE;
//			double lowest = Double.MAX_VALUE;
//			for (Double x : upper) {
//				double ans = b0 + b1*x + b2*(x*x) + (b3*(x*x*x));
//				if (ans < lowest)
//					lowest = ans;
//			}
//			for (Double x : lower) {
//				double ans = t0 + t1*x + t2*(x*x) + (t3*(x*x*x));
//				if (ans < uppest)
//					uppest = ans;
//			}
//			System.out.println(uppest-lowest);
			double max = Double.MIN_VALUE;
			double min = Double.MAX_VALUE;
			for (double x = 0.0; x <=1.0; x+=0.001) {
				double ans = (b0 + b1*x + b2*(x*x) + (b3*(Math.pow(x,  3))));
				System.out.println(ans);
				if (max > ans)
					max = ans;
			}
			for (double x = 0.0; x <=1.0; x+=0.00001) {
				double ans = (t0 + t1*x + t2*(x*x) + (t3*(Math.pow(x, 3))));
				//				System.out.println(ans);
				if (min < ans)
					min = ans;
			}
			System.out.println(Math.abs(max-min));
		}	
	}

	private static ArrayList<Double> quadraticForm(double a, double b, double c) {
		ArrayList<Double> sols = new ArrayList<Double>();
		double oppositeofb = -1*b;
		double bsquaredminus4ac = Math.pow(b, 2) - (4*a*c);
		sols.add((oppositeofb + Math.sqrt(bsquaredminus4ac))/(2*a));
		sols.add((oppositeofb - Math.sqrt(bsquaredminus4ac))/(2*a));
		return sols;
	}	
}

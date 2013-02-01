import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;


public class D {
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		int length = scan.nextInt();
		int marks = scan.nextInt();
		ArrayList<Point> tuples = new ArrayList<Point>();
		ArrayList<Integer> whereMarksAre = new ArrayList<Integer>();
		for (int i = 0; i < marks; i++) {
			whereMarksAre.add(scan.nextInt());
		}
		for (int i = 0; i < whereMarksAre.size(); i++) {
			for (int j = i + 1; j < whereMarksAre.size(); j++)
			{
				int start = whereMarksAre.get(i);
				int end = whereMarksAre.get(j);
				int period = end - start;
				if (period > start)
				tuples.add(new Point(start, period));
			}
		}
		System.out.print(tuples);
	}
}

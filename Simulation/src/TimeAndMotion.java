import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Solutions works for test1.in. It takes around 1.5 minutes for that input. It
 * takes too long to wait for the bigger input. My instinct tells me that there
 * is a math equation I can use to calculate the number without actually doing
 * the simulation, but I do not have the skill nor the experience to come up
 * with it
 * 
 * @author karthik
 * 
 */
public class TimeAndMotion {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			int n = scan.nextInt();
			if (n == 0)
				break;
			else {
				calculate(n);
			}
		}
	}

	/**
	 * Simulate the clock by keeping queues for all the respective different
	 * queue in the clock. Follow directions on how to move the balls around.
	 * 
	 * @param n
	 */
	public static void calculate(int n) {
		Queue<Integer> botQueue = new ArrayDeque<Integer>();
		Queue<Integer> minQueue = new ArrayDeque<Integer>();
		Queue<Integer> fiveMinQueue = new ArrayDeque<Integer>();
		Queue<Integer> hourQueue = new ArrayDeque<Integer>();
		for (int i = 0; i < n; i++) {
			botQueue.add(i);
		}
		// count represents each minute
		int count = 0;
		while (true) {
			count++;
			int top = botQueue.remove();
			minQueue.add(top);
			if (minQueue.size() == 5) {
				ArrayList<Integer> temp = new ArrayList<Integer>(minQueue);
				// reverse the four balls, put them back into the bottom queue
				// put one in the fiveMinQueue
				for (int i = 3; i >= 0; i--) {
					botQueue.add(temp.get(i));
					minQueue.remove();
				}
				fiveMinQueue.add(minQueue.remove());
			}
			if (fiveMinQueue.size() == 12) {
				ArrayList<Integer> temp = new ArrayList<Integer>(fiveMinQueue);
				for (int i = 10; i >= 0; i--) {
					botQueue.add(temp.get(i));
					fiveMinQueue.remove();
				}
				hourQueue.add(fiveMinQueue.remove());
			}
			if (hourQueue.size() == 12) {
				ArrayList<Integer> temp = new ArrayList<Integer>(hourQueue);
				for (int i = 10; i >= 0; i--) {
					botQueue.add(temp.get(i));
					hourQueue.remove();
				}
				botQueue.add(hourQueue.remove());

			}
			if (isSorted(botQueue, n)) {
				System.out.println(n + " balls cycle after " + count
						/ (24 * 60) + " days.");
				break;
			}
		}
	}

	/**
	 * Check if a queue is sorted (i.e. if it is in the initial configuration)
	 * 
	 * @param botQueue
	 * @param n
	 * @return
	 */
	private static boolean isSorted(Queue<Integer> botQueue, int n) {
		ArrayList<Integer> arr = new ArrayList<Integer>(botQueue);
		boolean result = false;
		if (arr.get(0) == 0 && arr.size() == n) {
			for (int i = 1; i < arr.size(); i++) {
				if ((arr.get(i) > arr.get(i - 1)))
					result = true;
				else
					return false;

			}
		}
		return result;
	}
}

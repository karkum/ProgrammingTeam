import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * This solution WORKS for the sample input and the sample judge data I found online
 * However, when I submit it to UVa, I get "Wrong Answer"
 */
public class TrafficLights {
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Light> lights = new ArrayList<Light>();
		while (true) {
			int next = scan.nextInt();
			if (next == 0) {
				int anotherNext = scan.nextInt();
				if (anotherNext == 0 && lights.size() == 0) {
					break;
				} else {
					calculateTime(lights);
					lights.clear();
				}
				if (anotherNext == 0 || !scan.hasNext())
					break;
				lights.add(new Light(anotherNext));
			} else {
				lights.add(new Light(next));
			}
		}
	}
	//Go for 5 hours, for each second, check if all the lights are green
	private static void calculateTime(ArrayList<Light> lights) {
		//Calculate the first green light to start the count there
		Collections.sort(lights);
		boolean found = true;
		for (int i = lights.get(0).green; i < 18000; i++) {
			found = true;
			for (Light l : lights) {
				if (l.isGreen(i)) {
					continue;
				}
				else {
					found = false;
					break;
				}
			}
			if (found) {
				int hours = i / 3600,
						remainder = i % 3600,
						minutes = remainder / 60,
						seconds = remainder % 60;

						System.out.println( ( (hours < 10 ? "0" : "") + hours
						+ ":" + (minutes < 10 ? "0" : "") + minutes
						+ ":" + (seconds< 10 ? "0" : "") + seconds ));

				break;
			}
		}
		if (!found) {
			System.out.println("Signals fail to synchronise in 5 hours");
		}
	}
	static class Light implements Comparable {
		int cycleTime, green, yellow, red;
		public Light(int time) {
			cycleTime = time;
			green = time - 5;
			yellow = 5;
			red = time;
		}
		@Override
		public int compareTo(Object other) {
			return cycleTime - ((Light)(other)).cycleTime;
		}
		//how to tell if the second we are on is green
		public boolean isGreen(int time) {
			return time % (2 * cycleTime) < (cycleTime - 5);
		}
	}
}

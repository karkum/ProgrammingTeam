import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
/**
works for small input. DOES NOT WORK FOR BIG INPUT. cannot handle the case for -1.
*/
public class ResistorCircuits {
	public static void main(String[] args) throws FileNotFoundException {
//		Scanner sc = new Scanner(new File("resistorin.txt"));
		Scanner sc = new Scanner(System.in);
		int cas = 0;
		while (true) {
			
			int N = sc.nextInt();
			if (N == 0)
				break;
			ArrayList<Resistor> resistors = new ArrayList<Resistor>(N);
			for (int i = 0; i < N; i++) {
				resistors.add(new Resistor(sc.next().charAt(0), sc.next()
						.charAt(0), sc.nextInt()));
			}
			while (resistors.size() != 1) {
				resistors = getGraph(resistors);
				
					
			}
			System.out.printf("Circuit %d: %.3f%n", ++cas, resistors.get(0).resistance);
		}
	}

	private static ArrayList<Resistor> getGraph(ArrayList<Resistor> resistors) {
		ArrayList<Resistor> newResistors = new ArrayList<Resistor>();
		if(isNotOk(resistors)) {
			newResistors.add(new Resistor('!', '?', -1));
			return newResistors;
		}
		ArrayList<Resistor> tempResistor = new ArrayList<Resistor>();
		for (int i = 0; i < resistors.size(); i++) {
			Resistor first = resistors.get(i);
			for (int j = i + 1; j < resistors.size(); j++) {
				Resistor second = resistors.get(j);
				if (isParallel(first, second)) {
					double newResi = 1 / ((1 / first.resistance) + (1 / second.resistance));
					newResistors.add(new Resistor(first.endpoint1,
							first.endpoint2, newResi));
					tempResistor.add(first);
					tempResistor.add(second);
				} else if (isSeries(first, second, resistors)) {
					if (!tempResistor.contains(first)) {
						char[] arr = findEnds(first, second);
						newResistors.add(new Resistor(arr[0], arr[1],
								first.resistance + second.resistance));
						tempResistor.add(first);
						tempResistor.add(second);
					}
				} else {
				}
			}
		}
		for (Resistor res : resistors) {
			if (!tempResistor.contains(res))
				newResistors.add(res);
		}
		return newResistors;
	}

	private static boolean isNotOk(ArrayList<Resistor> resistors) {
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < resistors.size(); i++) {
			Resistor one = resistors.get(i);
			set.add(one.endpoint1);
			set.add(one.endpoint2);
		}
		return !set.contains('A') && !set.contains('Z');
	}

	private static char[] findEnds(Resistor first, Resistor second) {
		char firstep1 = first.endpoint1;
		char firstep2 = first.endpoint2;
		char secondep1 = second.endpoint1;
		char secondep2 = second.endpoint2;
		if (firstep1 == secondep1)
			return new char[] { firstep2, secondep2 };
		else if (firstep1 == secondep2)
			return new char[] { firstep2, secondep1 };
		else if (firstep2 == secondep2)
			return new char[] { firstep1, secondep1 };
		else
			return new char[] { firstep1, secondep2 };
	}

	private static boolean isParallel(Resistor first, Resistor second) {
		char firstep1 = first.endpoint1;
		char firstep2 = first.endpoint2;
		char secondep1 = second.endpoint1;
		char secondep2 = second.endpoint2;
		return (firstep1 == secondep1 || firstep1 == secondep2)
				&& (firstep2 == secondep1 || firstep2 == secondep2);
	}

	private static boolean isSeries(Resistor first, Resistor second,
			ArrayList<Resistor> resistors) {
		// try the first resistor first
		boolean result = true;
		char firstep1 = first.endpoint1;
		char firstep2 = first.endpoint2;
		char secondep1 = second.endpoint1;
		char secondep2 = second.endpoint2;
		if (firstep1 == secondep1 || firstep1 == secondep2) {
			// check no other resistors have this endpoint
			for (Resistor r : resistors) {
				if (!r.equals(first) && !r.equals(second)) {
					if (r.isUsed(firstep1)) {
						result = false;
						break;
					}
				}
			}
		} else if (firstep2 == secondep1 || firstep2 == secondep2) {
			// check no other resistors have this endpoint
			for (Resistor r : resistors) {
				if (!r.equals(first) && !r.equals(second)) {
					if (r.isUsed(firstep2)) {
						result = false;
						break;
					}
				}
			}
		} else {
			return false;
		}
		return result;
	}

	private static class Resistor {
		double resistance;
		char endpoint1;
		char endpoint2;

		public Resistor(char one, char two, double res) {
			resistance = res;
			endpoint1 = one;
			endpoint2 = two;
		}

		public boolean isUsed(char c) {
			return endpoint1 == c || endpoint2 == c;
		}

		public String toString() {
			return "[" + endpoint1 + ", " + endpoint2 + ", " + resistance + "]";
		}
	}
}

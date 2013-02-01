import java.util.PriorityQueue;
import java.util.Scanner;

public class MainA {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		int numCases = sc.nextInt();
		for (int i = 0; i < numCases; i++) 	{
			sc.nextLine();
			PriorityQueue<Integer> g = new PriorityQueue<Integer>(); 
			PriorityQueue<Integer> mg = new PriorityQueue<Integer>(); 
			int n_g = sc.nextInt();
			int n_m = sc.nextInt();
			for (int k = 0; k < n_g; k++) {
				g.add(sc.nextInt());
			}
			for (int k = 0; k < n_m; k++) {
				mg.add(sc.nextInt());
			}
			findAnswer(g, mg);
		}
	}

	private static void findAnswer(PriorityQueue<Integer> g, PriorityQueue<Integer> mg) {
		boolean gWins = false;
		boolean mgWins = false;
		while (true) {
			if(g.size() == 0 && mg.size() != 0) {
				mgWins = true;
				break;
			}
			if(mg.size() == 0 && g.size() != 0) {
				gWins = true;
				break;
			}
			if (g.peek() < mg.peek())
				g.poll();
			else if (mg.peek() < g.peek()) {
				mg.poll();
			} else {
				//remove random from mg
				mg.poll();
			}
		}
		if (mgWins) 
			System.out.println("MechaGodzilla");
		else if (gWins) 
			System.out.println("Godzilla");
	
	}
}

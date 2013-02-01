import java.util.HashMap;
import java.util.Scanner;


public class MainC {
	static HashMap<String, String> map = new HashMap<String, String>();
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String line = sc.nextLine();
			if (line.equals("")) {
				solve(sc);
			}
			else {
				String [] a = line.split(" ");
				String key = a[0];
				String val = a[1];
				map.put(val, key);
			}
		}
	}
	private static void solve(Scanner sc) {
		while (sc.hasNext()) {
			String line = sc.next();
			if (!map.containsKey(line))
				System.out.println("eh");
			else
				System.out.println(map.get(line));
		}
	}
}

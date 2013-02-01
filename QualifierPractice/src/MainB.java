import java.util.HashMap;
import java.util.Scanner;

public class MainB {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashMap<Integer,Integer> list = new HashMap<Integer, Integer>();
		int j = 0;
		for (int i = 0; j <= 50000; i++) {
			String binary = Integer.toBinaryString((i));
			if (binary.equals(new StringBuilder(binary).reverse().toString())) {
//				list.put(j++, i);
				System.out.println("list.put(" + j++ + ", " + i + ");");
			}
		}
		while (sc.hasNext()) {
			int m = sc.nextInt();
			if (list.containsKey(m))
				System.out.println(list.get(m));

		}
	}
}

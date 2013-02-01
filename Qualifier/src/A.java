import java.util.Scanner;


public class A {
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()) {
			String line = scan.nextLine();
			if (line.toLowerCase().contains("problem"))
				System.out.println("yes");
			else 
				System.out.println("no");
		}
	}
}
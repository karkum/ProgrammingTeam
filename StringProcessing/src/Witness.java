import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Passes sample input, but didn't work on the ACM site, I get "wrong answer"
 * @author karthik
 *
 */
public class Witness {
	final static String MATCH_AFTER = "(?<=%1$s)";

	public static void main(String [] args) throws FileNotFoundException {
		ArrayList<String> list = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		while (true) {
			String line = scan.nextLine().trim();
			if (line.equals("EndOfInput"))
				break;
			while (!line.equals("EndOfList")) {
				list.add(line.toLowerCase().trim());
				line = scan.nextLine().trim();
			}
			String msg = scan.nextLine();
			StringBuilder builder = new StringBuilder();
			while (!msg.equals("EndOfMsg")) {
				builder.append(msg + "\n");
				msg = scan.nextLine();
			}
			solve(list, builder.toString());
			list.clear();
		}
	}

	private static void solve(ArrayList<String> list, String msg) {
		String delim = String.format(MATCH_AFTER, "[\\.\\!\\?]|\\n\\n");
		ArrayList<String> sentenses = parse(msg, delim);
		replaceWords(sentenses, list);
	}
	private static void replaceWords(ArrayList<String> sentences,
			ArrayList<String> listOfBadWords) {
		HashSet <String>setOfBadWords = new HashSet<String>(listOfBadWords);
		StringBuilder builder = new StringBuilder();
		for (String sent : sentences) {
			String str = sent;
			ArrayList <String> words = parse(sent, "\\b");
			for (String word : words) {
				if (!word.equals(" ")) { 
					String newWord = word.toLowerCase();
					if (setOfBadWords.contains(newWord)) {
						str = sent.replaceAll(".", "@");
						break;
					}
				} 
			}
			builder.append(str);
		}
		System.out.print(builder.toString());
		System.out.println("====");
	} 

	static ArrayList<String> parse(String input, String delim) {
		Scanner s = new Scanner(input).useDelimiter(delim);
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()) {
			list.add(s.next());
		}
		return list;
	}
}
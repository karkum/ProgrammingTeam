import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Passes ICPC Online judge in .624 seconds. 
 * @author karthik
 *
 */
public class Abstract {
	final static String MATCH_AFTER = "(?<=%1$s)";

	public static void main(String[] arg) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("in.txt"));
		String line = "";
		StringBuilder buff = new StringBuilder();
		while (true) {
			line = scan.nextLine();
			if (line.equals("******")) {
				solve(buff.toString());
				break;
			}
			if (line.equals("***")) {
				solve(buff.toString());
				buff = new StringBuilder();
			} else {
				buff.append(line + "\n");
			}
		}
	}

	//Solve this problem
	private static void solve(String string) {
		String paragraphs[] = string.split("\\r?\\r?\\n\\n");
		boolean done = false;
		for (String paragraph : paragraphs) {
			String topicSentence = getTopic(paragraph);
			if (!topicSentence.equals("")) {
				System.out.println(topicSentence.trim());
				done = true;
			}
		}
		if (!done)
			System.out.println();
		System.out.println("======");
	}
	//Find topic of each paragraph
	private static String getTopic(String paragraph) {
		String result = "";
//		String sentences[] = paragraph.split("[\\.\\!\\?]|\\n\\n");
		String delim = String.format(MATCH_AFTER, "[\\.\\!\\?]|\\n\\n");
		String [] sentences = parse(paragraph, delim);
		if (sentences.length >= 3) {
			int score = 0;
			int index = 0;
			int temp = 0;
			int inc = 0;
			for (String sent : sentences) {
				if (!sent.equals(" ") && !sent.equals("")) {
					if ((temp = findTopic(inc, sent, sentences)) > score) {
						score = temp;
						index = inc;
					}
					inc++;
				}
			}
			result += sentences[index];
		}
		return result;
	}

	/**
	 * This function looks through and find the topic sentence, add the words to
	 * a map, if we see it again, then keep track of how many distinct words there are
	 */
	private static int findTopic(int which, String sent, String[] sentences) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String[] words = sent.split("[\\p{P} \\t\\n\\r]");
		for (String word : words) {
			if (!word.equals("") && !word.equals(" ") && !word.equals("\n") && !word.equals("  ") )
				map.put(word.toLowerCase(), 0);
		}
		for (int i = which; i< sentences.length; i++) {
			if (!sent.equals(sentences[i])) {
				String[] wo = sentences[i].split("\\b");
				for (String w : wo) {
					if (!w.equals("") && !w.equals(" ") && !w.equals("\n") && !w.equals("  ")) {
						if (map.containsKey(w.toLowerCase())) {
							int c = map.get(w.toLowerCase());
							map.put(w.toLowerCase(), ++c);
						}
					}
				}
			}
		}
		int sum = 0;
		for (String s : map.keySet()) {
			if (map.get(s) > 0)
				sum ++;
		}
		return sum;
	}
	
	//Parse a given string with a given delimiter
	static String [] parse(String input, String delim) {
		Scanner s = new Scanner(input).useDelimiter(delim);
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()) {
			list.add(s.next());
		}
		String [] res = new String[list.size()];
		for (int i =0; i < list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * First try-out problem. Sorts the medal table according to a given measure.
 * 
 * I used `sort -R <inputfilename> <outputfilename>` to test my code.
 * 
 * Usage: java Sort < <inputfilename> > <sortedoutputfilename>
 * 
 * @author Karthik Kumar (kkumar91@vt.edu)
 * @version 08.17.2012
 */
public class Sort {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Entry> entries = new ArrayList<Entry>();
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] arr = line.split(",");
			String name = arr[0];
			int gold = Integer.valueOf(arr[1]);
			int silver = Integer.valueOf(arr[2]);
			int bronze = Integer.valueOf(arr[3]);
			int total = Integer.valueOf(arr[4]);
			entries.add(new Entry(name, gold, silver, bronze, total));
		}
		Collections.sort(entries);
		for (Entry e : entries) {
			System.out.println(e);
		}
	}

	/**
	 * Represents a single entry, with a name, and medal count. Also provides
	 * for a way to compare medal count
	 */
	private static class Entry implements Comparable<Entry> {
		private String name;
		private int gold;
		private int silver;
		private int bronze;
		private int total;

		public Entry(String n, int g, int s, int b, int t) {
			setName(n);
			setGold(g);
			setSilver(s);
			setBronze(b);
			setTotal(t);
		}

		public String toString() {
			return getName() + " " + gold + " " + silver + " " + bronze + " "
					+ total;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getGold() {
			return gold;
		}

		public void setGold(int gold) {
			this.gold = gold;
		}

		public int getSilver() {
			return silver;
		}

		public void setSilver(int silver) {
			this.silver = silver;
		}

		@SuppressWarnings("unused")
		public int getBronze() {
			return bronze;
		}

		public void setBronze(int bronze) {
			this.bronze = bronze;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		/**
		 * Compares this and other entry. First looks at whether this entry is
		 * as good as the other, and the other is not as good as this and vice
		 * versa. Otherwise returns the lexical comparison of their names.
		 * 
		 * @return >0 if other should be placed after, <0 if this should be
		 *         placed before
		 */
		@Override
		public int compareTo(Entry other) {
			if (isAsGood(this, other) && !isAsGood(other, this)) {
				return -1;
			} else if (isAsGood(other, this) && !isAsGood(this, other)) {
				return 1;
			}
			else {
				// Otherwise place either one first.
				return (int)(Math.random() * 2);
				//this.getName().compareTo(other.getName());
			}
		}

		/**
		 * Returns true if entry a fits the definiton of being as good as entry
		 * b
		 * 
		 * @param a
		 *            The first entry
		 * @param b
		 *            The other entry
		 * @return true if as is as good as b, false otherwise
		 */
		private boolean isAsGood(Entry a, Entry b) {
			if (a.getGold() >= b.getGold()) {
				if (a.getGold() + a.getSilver() >= b.getGold() + b.getSilver()) {
					if (a.getTotal() >= b.getTotal()) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
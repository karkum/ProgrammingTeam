import java.util.Random;

public class Monopoly {
	private static int ccCard;
	private static int chCard;

	public static void main(String[] args) {
		int[] counts = new int[40];
		// final int [] cc = { 2, 17, 33 };
		// final int [] ch = { 7, 22, 36 };
		Random rand = new Random();
		int currPos = 0;
		int numTimesdouble = 0;
		for (int i = 0; i < 1000000; i++) {
			// roll 4-sided die many times
			int dice1 = (rand.nextInt(4) + 1);
			int dice2 = (rand.nextInt(4) + 1);
			// if we rolled a double, keep track of how many we have done
			if (dice1 == dice2) {
				numTimesdouble++;
			} else {
				numTimesdouble = 0;
			}
			// if we have rolled 3 doubles, go to jail, dont move.
			if (numTimesdouble == 3) {
				counts[10]++;
				continue;
			}
			int moveLength = dice1 + dice2;
			// move the piece by moveLength mod 40, so we dont go over
			currPos = (currPos + moveLength) % 40;
			if (currPos == 36) {
				// if we land on this cc, see what card we draw, and move there
				int moveTo = doCH(currPos);
				if (moveTo != -1) {
					counts[moveTo]++;
					counts[currPos]++;
				} else {
					counts[currPos]++;
				}
				// if we have to go back 3 spaces
				if (moveTo == currPos - 3) {
					// do CC now
					int moveToAgain = doCC();
					if (moveToAgain != -1) {
						counts[moveToAgain]++;
						currPos = moveToAgain;
					} else {
						currPos = moveTo;
					}
				}
			} else if (currPos == 2 || currPos == 17 || currPos == 33) {
				// do any of the CC places
				int moveTo = doCC();
				if (moveTo != -1) {
					counts[currPos]++;
					counts[moveTo]++;
					currPos = moveTo;
				} else {
					counts[currPos]++;
				}
			} else if (currPos == 7 || currPos == 22) {
				int moveTo = doCH(currPos);
				if (moveTo != -1) {
					counts[currPos]++;
					counts[moveTo]++;
					currPos = moveTo;
				} else
					counts[currPos]++;
			} else if (currPos == 30) {
				// if its gotojail
				counts[10]++;
				currPos = 10;
			} else {
				counts[currPos]++;
			}
		}
		// Arrays.sort(counts);
		int maxi1 = 0;
		int maxi2 = 0;
		int maxi3 = 0;
		int max = 0;
		for (int j = 0; j < counts.length; j++) {
			if (counts[j] > max) {
				max = counts[j];
				maxi1 = j;
			}
		}
		max = 0;
		for (int j = 0; j < counts.length; j++) {
			if (counts[j] > max && j != maxi1) {
				max = counts[j];
				maxi2 = j;
			}

		}
		max = 0;
		for (int j = 0; j < counts.length; j++) {
			if (counts[j] > max && j != maxi2 && j != maxi1) {
				max = counts[j];
				maxi3 = j;
			}
		}
//		for (int j = 0; j < counts.length; j++) {
//			System.out.println("[" + j + "]" + counts[j]);
//		}
		System.out.printf("%02d%02d%02d\n", maxi1, maxi2, maxi3);
//		System.out.printf("%f %f %f", 100 * (double) counts[maxi1] / 1000000,
//				100 * (double) counts[maxi2] / 1000000,
//				100 * (double) counts[maxi3] / 1000000);
	}

	private static int doCH(int curr) {
		chCard = (chCard + 1) % 16;
		if (chCard == 1)
			return 0;
		if (chCard == 2)
			return 10;
		if (chCard == 3)
			return 11;
		if (chCard == 4)
			return 24;
		if (chCard == 5)
			return 39;
		if (chCard == 6)
			return 5;
		if (chCard == 7 || chCard == 8) {
			if (curr == 7)
				return 15;
			if (curr == 22)
				return 25;
			if (curr == 36)
				return 5;
		}
		if (chCard == 9) {
			if (curr == 7)
				return 12;
			if (curr == 22)
				return 28;
			if (curr == 36)
				return 12;
		}
		if (chCard == 10) {
			return (curr - 3) % 40;
		} else
			return -1;
	}

	private static int doCC() {
		ccCard = (ccCard + 1) % 16;
		if (ccCard == 1)
			return 0;
		else if (ccCard == 2)
			return 10;
		else
			return -1;
	}
}

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Karthik Kumar
 * 
 * Submitted to ICPC judge, ran in 2.628 seconds.
 *
 */
public class Ceramic {
	//Set up two maps, mapping the tiles to a list of its rotations
	static HashMap<Integer, ArrayList<Point[]>> tiles = new HashMap<Integer, ArrayList<Point[]>>();
	
	//Map a certain integer to its equivalent character. ex : 1->A, 2->B
	static HashMap<Integer, Character> chars = new HashMap<Integer, Character>();

	public static void main(String[] args) {
		//INPUT-----------------------------------------------------------
		Scanner scan = new Scanner(System.in);
		doMappings();
		int num_cases = scan.nextInt();
		int[] tiles = new int[9];
		for (int i = 0; i < num_cases; i++) {
			for (int j = 0; j < 9; j++) {
				tiles[j] = scan.nextInt();
			}
		//ENDINPUT------------------------------------------------------------
			boolean used[] = new boolean[9];
			char [][] board = new char[6][6];
			//set up an empty board with Os
			for (int k = 0; k < 6; k++) {
				for (int l = 0; l < 6; l++)
					board[k][l] = 'O';
			}
			System.out.printf("Data Set %d\n", i + 1);
			if (solve(board, tiles, used, new Point(0, 0)) == true) {
				System.out.println("The floor may be tiled.");
				printBoard(board);
			} else {
				System.out.println("The floor may not be tiled.");
			}
			System.out.println();
		}
		System.out.println("End of Output");
	}

	/**
	 * Iterate through the board left to right, top to bottom, return first Open spot or null
	 */
	private static Point returnNextTopLeft(char[][] board) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (board[i][j] == 'O')
					return new Point(i, j);
			}
		}
		return null;
	}

	private static boolean solve (char[][]board, int []thisTiles, boolean [] piecesUsed, Point topLeft) {
		//If we dont have any empty spots left
		if (topLeft == null) {
			return true;
		} else {
			//Go through each tile
			for (int i = 0; i < 9 ; i++) {
				//if we have already used it, look at nex tile
				if (piecesUsed[i]) {
					continue;
				}
				//if we havent used one, mark it as used, and try to place each of it and all its rotations
				piecesUsed[i] = true;
				for (Point[] p : tiles.get(thisTiles[i])) {
					@SuppressWarnings("rawtypes")
					ArrayDeque result = place(p, board, topLeft, chars.get(i + 1));
					//If we could place all 4, call solve again
					if (result.size() == 4) {
						boolean res = solve(board, thisTiles, piecesUsed, returnNextTopLeft(board));
						//If this tree didnt work out, remove the piece
						if (res)	
							return true;
						removePiece(p, board, topLeft);
					} 
				}
				//Mark it as unused if we werent able to return true above
				piecesUsed[i] = false;
			}
			return false;
		}

	}

	/**
	 * Remove a piece from the board
	 */
	private static void removePiece(Point[] pieces, char[][] board,
			Point where) {
		for (Point p : pieces) {
			board[p.x + where.x][p.y + where.y] = 'O';
		}
	}
	private static void printBoard(char [] []board) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}		
	}

	/**
	 * Place a piece on the board and return locations where we placed something in ArrayDeque
	 */
	public static ArrayDeque<Point> place(Point[] pieces, char[][] board,
			Point where, char c) {
		ArrayDeque<Point> placed = new ArrayDeque<Point>();
		try {
			for (Point p : pieces) {
				if (board[p.x + where.x][p.y + where.y] == 'O') {
					placed.add(new Point(p.x + where.x, p.y + where.y));
				}
			}
		} catch (Exception e) {
			return placed;
		}

		if (placed.size() == 4) {
			for (Point p : placed) {
				board[p.x][p.y] = c;
			}
			return placed;
		} else {
			return placed;
		}
	}

	
	//Set up the mappings
	public static void doMappings() {
		Point[] p1 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(0, 2), new Point(0, 3) };
		Point[] p1rot1 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(2, 0), new Point(3, 0) };
		ArrayList<Point[]> p1rotes = new ArrayList<Point[]>();
		p1rotes.add(p1);
		p1rotes.add(p1rot1);
		tiles.put(1, p1rotes);

		Point[] p2 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(1, 0), new Point(1, 1) };
		ArrayList<Point[]> p2rotes = new ArrayList<Point[]>(1);
		p2rotes.add(p2);
		tiles.put(2, p2rotes);

		Point[] p3 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(1, 1), new Point(1, 2) };
		Point[] p3rot1 = new Point[] { new Point(0, 0), new Point(1, -1),
				new Point(1, 0), new Point(2, -1) };
		ArrayList<Point[]> p3rotes = new ArrayList<Point[]>();
		p2rotes.add(p3);
		p3rotes.add(p3rot1);
		tiles.put(3, p3rotes);

		Point[] p4 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(1, -1), new Point(1, 0) };
		Point[] p4rot1 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(1, 1), new Point(2, 1) };
		ArrayList<Point[]> p4rotes = new ArrayList<Point[]>();
		p4rotes.add(p4);
		p4rotes.add(p4rot1);
		tiles.put(4, p4rotes);

		Point[] p5 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(1, 1), new Point(1, 2) };
		Point[] p5rot1 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(1, 0), new Point(2, 0) };
		Point[] p5rot2 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(0, 2), new Point(1, 2) };
		Point[] p5rot3 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(2, 0), new Point(2, -1) };
		ArrayList<Point[]> p5rotes = new ArrayList<Point[]>();
		p5rotes.add(p5);
		p5rotes.add(p5rot1);
		p5rotes.add(p5rot2);
		p5rotes.add(p5rot3);
		tiles.put(5, p5rotes);

		Point[] p6 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(0, 2), new Point(1, 0) };
		Point[] p6rot1 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(1, 1), new Point(2, 1) };
		Point[] p6rot2 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(1, -1), new Point(1, -2) };
		Point[] p6rot3 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(2, 0), new Point(2, 1) };
		ArrayList<Point[]> p6rotes = new ArrayList<Point[]>();
		p6rotes.add(p6);
		p6rotes.add(p6rot1);
		p6rotes.add(p6rot2);
		p6rotes.add(p6rot3);
		tiles.put(6, p6rotes);

		Point[] p7 = new Point[] { new Point(0, 0), new Point(1, -1),
				new Point(1, 0), new Point(1, 1) };
		Point[] p7rot1 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(1, 1), new Point(2, 0) };
		Point[] p7rot2 = new Point[] { new Point(0, 0), new Point(0, 1),
				new Point(0, 2), new Point(1, 1) };
		Point[] p7rot3 = new Point[] { new Point(0, 0), new Point(1, 0),
				new Point(2, 0), new Point(1, -1) };
		ArrayList<Point[]> p7rotes = new ArrayList<Point[]>();
		p7rotes.add(p7);
		p7rotes.add(p7rot1);
		p7rotes.add(p7rot2);
		p7rotes.add(p7rot3);
		tiles.put(7, p7rotes);

		for (int i = 1; i <= 9; i++) {
			chars.put(i, (char) ('A' + i - 1));
		}
	}
}

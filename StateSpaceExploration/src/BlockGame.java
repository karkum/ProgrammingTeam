import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/**
 * Karthik Kumar
 * Passes ICPC judge in 14.8 seconds. I represented a block as a list of points, a direction
 * and whether it was vertical or horizontal. I represented the board as a 1-dimentional
 * char array.
 *
 */
public class BlockGame {
	public final static boolean HORIZONTAL = false;
	public final static boolean VERTICAL = true;
	public static char target;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			target = scan.next().charAt(0);
			if (target == '*')
				break;
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i <= 6; i++) {
				builder.append(scan.nextLine());
			}
			//Convert each line into a row in a char array
			char[] arr = builder.toString().toCharArray();
			State first = new State(arr, boardToBlocks(arr));
			if (!solve(first))
				System.out.println("-1");
		}
	}

	static boolean solve(State start) {
		Set<State> visited = new HashSet<State>(); 
		Map<State, State> pred = new HashMap<State, State>(); 
		Map<State, Integer> dist = new HashMap<State, Integer>(); 
		Deque<State> bfs = new ArrayDeque<State>(); // BFS queue
		bfs.offer(start);
		dist.put(start, 0);
		if (start.isfinal()){
			output(1);
			return true;
		}
		while (bfs.size() > 0) {
			State s = bfs.poll();
			int n = dist.get(s);
			visited.add(s);

			for (State succ : s.successors()) {
				if (visited.contains(succ))
					continue;

				if (!pred.containsKey(succ))
					pred.put(succ, s);

				if (succ.isfinal()) {
					output(n + 1);
					return true;
				}

				if (!dist.containsKey(succ)) {
					dist.put(succ, n + 1);
					bfs.offer(succ);
				}
			}
		}
		return false;
	}

	/* Compute and output path */
	static void output(int distToSolution) {
		System.out.println(distToSolution);
	}

	
	//Convert a one dimensional board to a list of blocks.
	@SuppressWarnings("unused")
	public static ArrayList<Block> boardToBlocks(char[] board) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		for (int i = 0; i < 36; i++) {
			if (board[i] != '.') {
				char here = board[i];
				ArrayList<Integer> points = new ArrayList<Integer>();
				boolean tar = here == target;
				char oneDown, twoDown;
				if (i - 6 >= 0 && board[i - 6] == here) {
					continue;
				}
				if (i + 6 < 36 && board[i + 6] == here) {
					oneDown = board[i + 6];
					points.add(i + 6);
				}
				if (i + 12 < 36 && board[i + 12] == here) {
					twoDown = board[i + 12];
					points.add(i + 12);
				}
				if (points.size() > 0) {
					points.add(0, i);
					blocks.add(new Block(VERTICAL, tar, points));
				}
			}
		}

		for (int i = 0; i < 36; i++) {
			if (board[i] != '.') {
				int row = i / 6;
				char here = board[i];
				ArrayList<Integer> points = new ArrayList<Integer>();
				boolean tar = here == target;
				char oneRight, twoRight;

				if ((i - 1) / 6 == row && i - 1 >= 0 && board[i - 1] == here) {
					continue;
				}
				if ((i + 1) / 6 == row && i + 1 < 36 && board[i + 1] == here) {
					oneRight = board[i + 1];
					points.add(i + 1);
				}
				if ((i + 2) / 6 == row && i + 2 < 36 && board[i + 2] == here) {
					twoRight = board[i + 2];
					points.add(i + 2);
				}
				if (points.size() > 0) {
					points.add(0, i);
					blocks.add(new Block(HORIZONTAL, tar, points));
				}
			}
		}
		return blocks;
	}

	public static class Block {
		boolean direction;
		boolean target;
		ArrayList<Integer> points;

		public Block(boolean direction, boolean target,
				ArrayList<Integer> points) {
			this.direction = direction;
			this.target = target;
			this.points = points;
		}
		public Block clone() {
			return new Block(direction, target, points);
		}
	}

	//Represent a state as a list of blocks and a current board
	public static class State {
		char[] board;
		ArrayList<Block> listOfBlocks;

		public String toString() {
			return Arrays.toString(board);
		}

		public State(char[] board, ArrayList<Block> blocks) {
			this.board = board;
			listOfBlocks = blocks;
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(board);
		}

		@Override
		public boolean equals(Object other) {
			return Arrays.equals(board, ((State) other).board);
		}

		public boolean isfinal() {
			for (Block b : listOfBlocks) {
				if (b.target == true) {
					int rightLoc = b.points.get(b.points.size() - 1);
					return rightLoc == 5 || rightLoc == 11 || rightLoc == 17
							|| rightLoc == 22 || rightLoc == 29
							|| rightLoc == 35;
				}
			}
			return false;
		}
		
		ArrayDeque<State> successors() {
			ArrayDeque<State> states = new ArrayDeque<State>();
			outer: for (int k = 0; k < listOfBlocks.size(); k++) {
				Block b = listOfBlocks.get(k);
				if (b.direction == HORIZONTAL) {
					int leftPoint = b.points.get(0);
					int rightPoint = b.points.get(b.points.size() - 1);
					int leftBound = (leftPoint / 6) * 6;
					int rightBound = (rightPoint / 6) * 6 + 6;
					for (int i = leftPoint - 1; i >= leftBound; i--) {
						if (board[i] == '.') {
							char[] clonedBoard = board.clone();
							ArrayList<Block> clonedBlocks = new ArrayList<Block>();
							for (Block bl : listOfBlocks) {
								clonedBlocks.add((Block) bl.clone());
							}
							ArrayList<Integer> points = new ArrayList<Integer>();
							removeBlock(b, clonedBoard);
							for (int j = 0; j < b.points.size(); j++) {
								clonedBoard[i + j] = board[leftPoint];
								points.add(i+j);
							}
							clonedBlocks.set(k, new Block(b.direction, b.target, points));
							states.add(new State(clonedBoard, clonedBlocks));
						} else {
							break;
						}
					}
					for (int i = rightPoint + 1; i < rightBound; i++) {
						if (board[i] == '.') {
							char[] clonedBoard = board.clone();
							ArrayList<Block> clonedBlocks = new ArrayList<Block>();
							for (Block bl : listOfBlocks) {
								clonedBlocks.add((Block) bl.clone());
							}
							ArrayList<Integer> points = new ArrayList<Integer>();
							removeBlock(b, clonedBoard);
							if (b.points.size() == 2) {
								for (int j = 0; j < b.points.size(); j++) {
									clonedBoard[(i - 1) + j] = board[rightPoint];
									points.add((i-1) + j);
								}
							} else {
								for (int j = 0; j < b.points.size(); j++) {
									clonedBoard[(i - 2) + j] = board[rightPoint];
									points.add((i-2) + j);
								}
							}
							clonedBlocks.set(k, new Block(b.direction, b.target, points));
							states.add(new State(clonedBoard, clonedBlocks));
						} else {
							continue outer;
						}
					}

				} else {
					int topPoint = b.points.get(0);
					int botPoint = b.points.get(b.points.size() - 1);
					int topBound = topPoint % 6;
					int botBound = topBound + 30;
					for (int i = topPoint - 6; i >= topBound; i-=6) {
						if (board[i] == '.') {
							char[] clonedBoard = board.clone();
							ArrayList<Block> clonedBlocks = new ArrayList<Block>();
							for (Block bl : listOfBlocks) {
								clonedBlocks.add((Block) bl.clone());
							}
							ArrayList<Integer> points = new ArrayList<Integer>();
							removeBlock(b, clonedBoard);
							for (int j = 0; j < b.points.size(); j++) {
								clonedBoard[i + (j*6)] = board[topPoint];
								points.add(i+ (6* j));
							}
							clonedBlocks.set(k, new Block(b.direction, b.target, points));
							states.add(new State(clonedBoard, clonedBlocks));
						} else {
							break;
						}
					}
					for (int i = botPoint + 6; i <= botBound; i+=6) {
						if (board[i] == '.') {
							char[] clonedBoard = board.clone();
							ArrayList<Block> clonedBlocks = new ArrayList<Block>();
							for (Block bl : listOfBlocks) {
								clonedBlocks.add((Block) bl.clone());
							}
							ArrayList<Integer> points = new ArrayList<Integer>();
							removeBlock(b, clonedBoard);
							if (b.points.size() == 2) {
								for (int j = 0; j < b.points.size(); j++) {
									clonedBoard[(i - 6) + (j*6)] = board[topPoint];
									points.add((i - 6) + (6* j));
								}
							} else {
								for (int j = 0; j < b.points.size(); j++) {
									clonedBoard[(i -12) + (j*6)] = board[topPoint];
									points.add((i - 12) + (6* j));
								}
							}
							clonedBlocks.set(k, new Block(b.direction, b.target, points));
							states.add(new State(clonedBoard, clonedBlocks));
						} else {
							continue outer;
						}
					}
				}
			}
			return states;
		}

		private void removeBlock(Block b, char[] board) {
			for (Integer p : b.points) {
				board[p] = '.';
			}
		}
	}
}

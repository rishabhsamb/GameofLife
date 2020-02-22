package gameoflife;
import java.util.*;

import javax.swing.*;
import java.awt.*;

public class GameofLife extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[][] board;

	public GameofLife(int[][] board, boolean hasBoard) {
		setTitle("Conway's Game of Life");
		setSize(1015, 1015);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.board = board;
	}
	
	void setBoard(int[][] board) {this.board = board;}
	
	public void paint(Graphics g) {
		for(int q = 5; q <= 5*200; q += 5) {
			for(int r = 5; r <= 5*200; r+= 5) {
				if(board[q/5][r/5] == 1) {
					g.setColor(new Color(0, 0, 0));
				}
				else {
					g.setColor(new Color(255, 255, 255));
				}
				g.fillRect(q, r, 4, 4);
			}
		}
	}
	
	public static void main(String[] args) {

		
		//creation of initial game state
		System.out.println("Welcome to John Conway's Game of Life. This grid is currently 200x200.");
		@SuppressWarnings("resource")
		Scanner inp = new Scanner(System.in);
		int row = 200;
		int col = 200;
		
		System.out.println("Provide the number of initially alive cells. (Total Cells: 40 000)");
		int numAlive = inp.nextInt();
		int[][] board = new int[row+2][col+2];
		Random rand = new Random();
		
		//creation of board, filled with zeroes. The board is one greater than the chosen dimensions, creating a border of zeroes.
		for(int i = 0; i <= row+1; i++) {
			for(int j = 0; j <= col+1; j++) {
				board[i][j] = 0;
			} 
		}
		
		//creation of 'alive cells'
		System.out.println("Would you like randomly generated alive cells? (y for yes, n for no)");
		if(inp.next().contains("y")) {
			for(int l = 0; l < numAlive; l++) {
				int q = rand.nextInt(row-1) + 1;
				int r = rand.nextInt(col-1) + 1;
				if(board[q][r] == 1) {
					l--;
				}
				else {
					board[q][r] = 1;
				}

			}
				
		}
		else {
			System.out.println("Provide the row, then (on the next line) the column of your alive cells.");
			for(int l = 0; l < numAlive; l++) {
				board[inp.nextInt()][inp.nextInt()] = 1;
			}
		}
		
		//window creation
        GameofLife gol = new GameofLife(board, true);
        gol.paint(gol.getGraphics());
		
		//print board
        /*
		System.out.println("The initial board: ");
		for(int k = 0; k <= row+1; k++) {
			System.out.println(Arrays.toString(board[k]));
		}
		*/
		
		//generate all new generations
		System.out.println("how many generations would you like to observe? We recommend 1 billion (30 years)");
		int q = inp.nextInt();
		for(int k = 0; k < q; k++) {
			gol.setBoard(generator(gol.board,row,col));
			System.out.println("Generation = " + k);
			gol.update(gol.getGraphics());
			
			//rest one second between generations 
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("finished");
		
	}
		
	public static int[][] generator(int[][] board, int row, int col) {
		//change board
		int[][] newBoard = new int[board.length][];
		for(int t = 0; t < board.length; t++) {
			newBoard[t] = board[t].clone();
		}
		
		//loop through all applicable cells
		for(int m = 1; m <= row; m++) {
			for(int n = 1; n <= col; n++) {
				int aliveCheck = 0;
				
				//check for live neighbors
				if(board[m][n-1] == 1) {
					aliveCheck += 1;
				}
				if(board[m][n+1] == 1) {
					aliveCheck += 1;
				}
				if(board[m-1][n-1] == 1) {
					aliveCheck += 1;
				}
				if(board[m-1][n] == 1) {
					aliveCheck += 1;
				}
				if(board[m-1][n+1] == 1) {
					aliveCheck += 1;
				}
				if(board[m+1][n-1] == 1) {
					aliveCheck += 1;
				}
				if(board[m+1][n] == 1) {
					aliveCheck += 1;
				}
				if(board[m+1][n+1] == 1) {
					aliveCheck += 1;
				}
				
				//make necessary changes
				if(aliveCheck == 3) {
					if(board[m][n] == 0) {
						newBoard[m][n] = 1;
					}
				}
				
				else if(aliveCheck != 2) {
					newBoard[m][n] = 0;
				}
			}
		}
		
		//print final board
		/*
		for(int k = 0; k <= row+1; k++) {
			System.out.println(Arrays.toString(newBoard[k]));
		}
		*/
		return newBoard;
	}
}

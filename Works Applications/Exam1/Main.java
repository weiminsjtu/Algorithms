/**
 * @author Wei Min
 * @time 2016-03-16
 * @name Game:new Gluttonous snake
 * @method Dynamic Programming
 */
import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
	/*get highest score from one begin cell for one target cell*/
	public static int getScore(int grid[][], int k, int i, int j, int pre) {
		int sumNotTele = 0;
		int sumTele = 0;
		int row = grid.length;

		if (k == i)// snake arrives at target cell
			return pre + grid[i][j];

		// go towards target cell, without teleport
		for (int m = Math.min(k, i); m <= Math.max(k, i); m++) {
			if (grid[m][j] == -1) {// block existed on the path
				sumNotTele = -1;
				break;
			}
			sumNotTele += grid[m][j]; //
		}

		// teleport to target cell
		if (k < i) {
			// go up to teleport
			for (int m = 0; m <= k; m++) {
				if (grid[m][j] == -1) {
					sumTele = -1;
					break;
				}
			}
			if (sumTele != -1) {// no block up
				for (int m = k; m < row; m++) {
					if (grid[m][j] == -1) {
						sumTele = -1;
						break;
					}
					sumTele += grid[m][j];
				}
			}
		} else {
			// go down to teleport
			for (int m = k; m < row; m++) {
				if (grid[m][j] == -1) {
					sumTele = -1;
					break;
				}
			}
			if (sumTele != -1) {// no block down
				for (int m = 0; m <= k; m++) {
					if (grid[m][j] == -1) {
						sumTele = -1;
						break;
					}
					sumTele += grid[m][j];
				}
			}
		}

		if (sumNotTele != -1)
			sumNotTele += pre;

		return Math.max(sumNotTele, sumTele);
	}
	
	/*get highest score from all begin cells for one target cell*/
	public static int getHighest(int grid[][], int highest_score[][], int i, int j) {
		int highest = -1;// highest score
		int score = -1;// one score
		int row = grid.length;
		int pre = -1;// left cell's value

		// different begin cell that snake gets from last column
		for (int k = 0; k < row; k++) { 
			if (j > 0)
				pre = highest_score[k][j - 1];
			else
				pre = 0;

			if (pre == -1)// if left cell is blocked
				continue;

			score = getScore(grid, k, i, j, pre);
			if (score > highest) {
				highest = score;
			}
		}

		return highest;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(new BufferedInputStream(System.in));
		int n = scan.nextInt();// number of rows
		int m = scan.nextInt();// number of columns
		int[][] grid = new int[n][m];// the grid
		int[][] highest_score = new int[n][m];//highest score when every cell is the end
		int highest = -1;//game final result

		// grid values
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				grid[i][j] = scan.nextInt();
			}
		}

		// traverse columns and rows,get highest score when snake stops at grid[i][j]
		for (int j = 0; j < m; j++) {
			for (int i = 0; i < n; i++) {
				if (grid[i][j] == -1) {
					highest_score[i][j] = -1;
				} else {
					highest_score[i][j] = getHighest(grid, highest_score, i, j);
				}
			}
		}
		for (int i = 0; i < n; i++) {
			if (highest_score[i][m - 1] > highest) {
				highest = highest_score[i][m - 1];
			}
		}
		System.out.println(highest);
		scan.close();
	}
}

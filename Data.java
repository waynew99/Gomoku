// CS 201 Final Project - Gomoku
// Yichen Yang / Wayne Wang

// Stores the position and color of the pieces
// Provide functions to offer hint and determine victory


public class Data {

	// Instance variables
	protected int[][] d;	// a 2D array that stores the current board
	protected int x = 15;	// the size of the board
	protected int y = 15;

	// Constructor

	// set up an empty board
	public Data() {
		d = new int[x][y];
		for (int i=0; i<x; i++) {
			for (int j=0; j<y; j++) {
				d[i][j] = 0;	// 0 is empty, 1 is black, 2 is white
			}
		}
	}


	// Instance methods

	// set the board to empty
	public void clear() {
		for (int i=0; i<x; i++) {
			for (int j=0; j<y; j++) {
				d[i][j] = 0;	// 0 is empty, 1 is black, 2 is white
			}
		}
	}

	
	// return the value stored at (i, j)
	public int get(int i, int j) {
		return d[i][j];
	}

	
	// set (i, j) to the given color, if it's empty
	public void set(int i, int j, boolean blackWhite) {
		if (d[i][j] == 0) {
			d[i][j] = blackWhite ? 1 : 2;
		}
	}

	
	// reset (i, j) to the empty
	public void remove(int i, int j) {
		d[i][j] = 0;
	}

	
	// search the number of occurrences of a color
	public int searchColor(boolean blackWhite) {
		int color = blackWhite ? 1 : 2;
		int count = 0;
		for (int i=0; i<15; i++) {
			for (int j=0; j<15; j++) {
				if (d[i][j] == color) count++;
			}
		}
		return count;
	}


	// check if one player has won the game
	public boolean checkVictory(boolean player) {
		String win = player ? "11111" : "22222";
		for (int i=0; i<15; i++) {
			for (int j=0; j<15; j++) {
				if (patternCheck(win, i, j))
					return true;
			}
		}
		return false;
	}
	

	// helper function that converts three integers into a integer list
	public int[] makeHint(int i, int j, int value) {
		int[] hint = new int[]{i, j, value};
		return hint;
	}


	// uses all of the pattern checking functions to find the best move 
	// and returns it as an integer list to represent the coordinates of the position to play
	public int[] getHint(boolean nextPlayer) {
		int[] bestChoice = makeHint(0, 0, -99999);
		for(int i=0; i<15; i++) {
			for(int j=0; j<15; j++){
				if (this.get(i, j) == 0) {
					int[] result = checkAlgorithm(i, j, nextPlayer);
					if (result[2] > bestChoice[2]) bestChoice = result;
				}
			}
		}
		return bestChoice;
	}



	// in the targetPattern string, 0 for empty, 1 for black, 2 for white
	// for all four directions, search for the pattern that is represented by the targetPattern string
	// and return true if there is at least one match
	public boolean patternCheck(String targetPattern, int x, int y) {
		int l = targetPattern.length();
		
		// for each position the point is in targetPattern
		for(int i=0; i<l; i++) {
			if (patternCheckHorizontal(i, l, x, y).equals(targetPattern) ||
				patternCheckVertical(i, l, x, y).equals(targetPattern) ||
				patternCheckULLR(i, l, x, y).equals(targetPattern) ||
				patternCheckURLL(i, l, x, y).equals(targetPattern)) {
				return true;
			}
		}
		return false;
	}


	// In the targetPattern string, 0 for empty, 1 for black, 2 for white
	// helper function of patternCheck, obtains the pattern of a sequence of positions on
	// the horizontal direction that has the chosen point at index i
	public String patternCheckHorizontal(int i, int l, int x, int y) {
			String current = "";
			int firstCoor = x - l + i + 1;
			int lastCoor = x + i;
			if (firstCoor>=0 && lastCoor<15) {
				// puts number representation of actual points in current[]
				for (int j=firstCoor; j<=lastCoor; j++) {
					current = current + Integer.toString(this.get(j, y));
				}
				return current;
			} else return "";
	}

	// In the targetPattern string, 0 for empty, 1 for black, 2 for white
	// helper function of patternCheck, obtains the pattern of a sequence of positions on
	// the vertical direction that has the chosen point at index i
	public String patternCheckVertical(int i, int l, int x, int y) {
			String current = "";
			int firstCoor = y - l + i + 1;
			int lastCoor = y + i;
			if (firstCoor>=0 && lastCoor<15) {
				// puts number representation of actual points in current[]
				for (int j=firstCoor; j<=lastCoor; j++) {
					current = current + Integer.toString(this.get(x, j));
				}
				return current;
			} else return "";
	}

	// In the targetPattern string, 0 for empty, 1 for black, 2 for white
	// helper function of patternCheck, obtains the pattern of a sequence of positions on
	// the upper right to lower left direction that has the chosen point at index i
	public String patternCheckULLR(int i, int l, int x, int y) {
			String current = "";
			int firstxCoor = x - l + i + 1;
			int lastxCoor = x + i;
			int firstyCoor = y - l + i + 1;
			int lastyCoor = y + i;
			if (firstxCoor>=0 && lastxCoor<15 && firstyCoor>=0 && lastyCoor<15) {
				// puts number representation of actual points in current[]
				for (int a=0; a<l; a++) {
					current = current + Integer.toString(this.get(firstxCoor+a, firstyCoor+a));
				}
				return current;
			} else return "";
	}

	// In the targetPattern string, 0 for empty, 1 for black, 2 for white
	// helper function of patternCheck, obtains the pattern of a sequence of positions on
	// the upper left to lower right direction that has the chosen point at index i
	public String patternCheckURLL(int i, int l, int x, int y) {
			String current = "";
			int firstxCoor = x + l - 1 - i;
			int lastxCoor = x - i;
			int firstyCoor = y - l + i + 1;
			int lastyCoor = y + i;
			if (firstxCoor<15 && lastxCoor>=0 && firstyCoor>=0 && lastyCoor<15) {
				// puts number representation of actual points in current[]
				for (int a=0; a<l; a++) {
					current = current +
								Integer.toString(this.get(firstxCoor-a, firstyCoor+a));
				}
				return current;
			} else return "";
	}


	public int[] checkAlgorithm(int i, int j, boolean nextPlayer) {
		int[] finalHint = new int[]{i, j, -9999};

		// value array
		int[] value = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
		if(!nextPlayer)	// the patterns have different values to white pieces
			value = new int[]{1,0,3,2,5,4,8,9,6,7,10,11,15,14,13,12,17,16};

		if ((searchColor(nextPlayer) == 0) && (searchColor(!nextPlayer) == 0))
			return makeHint(7, 7, -100);

		// defending move to disable the pattern "xx"
		this.set(i, j, false);
		if (patternCheck("22", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[0];
		}
		this.remove(i, j);

		// attacking move to achieve the pattern "xx"
		this.set(i, j, true);
		if (patternCheck("11", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[1];
		}
		this.remove(i, j);

		// attacking move to achieve the pattern "oxxo"
		this.set(i, j, true);
		if (patternCheck("0110", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[3];
		}
		this.remove(i, j);

		// attacking move to achieve the pattern "xxx"
		this.set(i, j, false);
		if (patternCheck("0220", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[2];
		}
		this.remove(i, j);

		// defending move to disable the pattern "oxxxo"
		this.set(i, j, false);
		if (patternCheck("02220", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[4];
		}
		this.remove(i, j);

		// attacking move to achieve the pattern "oxxxo"
		this.set(i, j, true);
		if (patternCheck("01110", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[5];
		}
		this.remove(i, j);

		// defending move to disable the pattern "ooxxxoo"
		this.set(i, j, false);
		if (patternCheck("0022200", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[6];
		}
		this.remove(i, j);

		// defending move to disable the pattern "xxxx"
		this.set(i, j, false);
		if ((patternCheck("02222", i, j)) ||
			(patternCheck("22220", i, j))) {
			this.remove(i, j);
			finalHint[2] = value[7];
		}
		this.remove(i, j);

		// attacking move to achieve the pattern "ooxxxoo"
		this.set(i, j, true);
		if (patternCheck("0011100", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[8];
		}
		this.remove(i, j);

		// attacking move to achieve the pattern "xxxxo"
		this.set(i, j, true);
		if (patternCheck("11110", i, j) ||
			patternCheck("01111", i, j)) {
			this.remove(i, j);
			finalHint[2] = value[9];
		}
		this.remove(i, j);

		// attacking move to achieve two "xxx" type patterns on the same point
		// this pattern guarantees win if there are enough moves
		this.set(i, j, nextPlayer);
		if(checkDoubleThree(i, j, nextPlayer)) {
			this.remove(i, j);
			finalHint[2] = value[10];
		}
		this.remove(i, j);

		// defending move to disable two "xxx" type patterns on the same point
		// this pattern guarantees win if there are enough moves
		this.set(i, j, !nextPlayer);
		if(checkDoubleThree(i, j, !nextPlayer)) {
			this.remove(i, j);
			finalHint[2] = value[11];
		}
		this.remove(i, j);


		// defending move to disable the pattern "oxxxxo"
		// this pattern guarantees win
		this.set(i, j, false);
		if (patternCheck("022220", i, j)){
			this.remove(i, j);
			finalHint[2] = value[12];
		}
		this.remove(i, j);

		// attacking move to achieve a "xxxx" type and a "xxx" type on the same position
		// this pattern guarantees win if there are enough moves
		this.set(i, j, true);
		if ((patternCheck("11110", i, j)) ||
			(patternCheck("01111", i, j))) {
			if ((patternCheck("001110", i, j)) ||
				(patternCheck("011100", i, j)) ||
				(patternCheck("010110", i, j)) ||
				(patternCheck("011010", i, j))) {
					this.remove(i, j);
					finalHint[2] = value[13];
				}
		}

		// defending move to disable a "xxxx" type and a "xxx" type on the same position
		// this pattern guarantees win if there are enough moves
		this.set(i, j, false);
		if ((patternCheck("22220", i, j)) ||
			(patternCheck("02222", i, j))) {
			if ((patternCheck("002220", i, j)) ||
				(patternCheck("022200", i, j)) ||
				(patternCheck("020220", i, j)) ||
				(patternCheck("022020", i, j))) {
					this.remove(i, j);
					//System.out.println("Defend 4&3");
					finalHint[2] = value[14];
				}
		}

		// attacking move to achieve "oxxxxo"
		// this pattern guarantees win
		this.set(i, j, true);
		if (patternCheck("011110", i, j)){
			this.remove(i, j);
			finalHint[2] = value[15];
		}
		this.remove(i, j);

		// defending move to disable "xxxxx"
		// this pattern guarantees win
		this.set(i, j, false);
		if (patternCheck("22222", i, j)){
			this.remove(i, j);
			finalHint[2] = value[16];
		}
		this.remove(i, j);

		// attacking move to achieve "xxxxx" and win
		this.set(i, j, true);
		if (patternCheck("11111", i, j)){
			this.remove(i, j);
			finalHint[2] = value[17];
		}
		this.remove(i, j);

		
		return finalHint;
	}


	// helper function that checks if a position has two "xxx" type patterns
	public boolean checkDoubleThree(int x, int y, boolean nextPlayer) {
		String[] doubleThree = new String[4];
		if (nextPlayer)
			doubleThree = new String[]{"011100", "001110", "010110", "011010"};
		else
			doubleThree = new String[]{"022200", "002220", "020220", "022020"};

		int[] statusList = new int[]{0, 0, 0, 0};

		for(int s=0; s<4; s++){
			String targetPattern = doubleThree[s];
			int l = targetPattern.length();
			// checks each pattern in each position on each direction
			for(int i=0; i<l; i++) {
				if (patternCheckHorizontal(i, l, x, y).equals(targetPattern)) statusList[0] = 1;
				if (patternCheckVertical(i, l, x, y).equals(targetPattern)) statusList[1] = 1;
				if (patternCheckURLL(i, l, x, y).equals(targetPattern)) statusList[2] = 1;
				if (patternCheckULLR(i, l, x, y).equals(targetPattern))	statusList[3] = 1;
			}
		}

		// counts the number of "xxx" patterns
		int status = 0;
		for (int i=0; i<4; i++) {
			status += statusList[i];
		}
		
		if (status >= 2) return true;
		else return false;
	}

}

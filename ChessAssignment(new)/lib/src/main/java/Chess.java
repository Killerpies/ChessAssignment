import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * This class will read a file containing a chess board and then determine if a king is in check or not
 * if a king is in check it will return the piece that has checked the king
 * 
 * White side it capitol letters
 * Black side is non-capitol letters
 * @author Justin Sanders
 *
 */
public class Chess {
	/**
	 * Function reads chess board and then determines if a king was checked and returns the piece that checked it
	 * @param file - file with 8x8 chess board inside
	 * @return char - representing which piece on the board has checked a king
	 */
	public static char go(File file) {

		
		String[] board = new String[8];
		  try {
		      Scanner myReader = new Scanner(file);
		      int index = 0;
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
//		        System.out.println(data);
		        board[index] = data;
		        index += 1;
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  // test for commit
		  // Checking for Kings
			for (int y = 0; y < 8 ; y++) {
				for (int x = 0; x < 8 ; x++) {
					if (board[y].charAt(x) == 'k') {
						//Black King
						// find white pieces in range of black king
						
						if (Chess.checkPawn(board, 'P',  x,  y))
							return 'P';
						
						if (Chess.checkKnight(board,  'N',  x,  y))
							return 'N';
						
						if (Chess.checkQueen(board,  'Q',  x,  y))
							return 'Q';
						
						if (Chess.checkBishop(board,  'B',  x,  y))
							return 'B';
						
						if (Chess.checkRook(board,  'R',  x,  y))
							return 'R';
						
						
						if (Chess.checkKing(board,  'K',  x,  y))
							return 'K';
						
					}
					
					if (board[y].charAt(x) == 'K') {
						// White King

						if (Chess.checkPawn(board, 'p',  x,  y))
							return 'p';
						
						if (Chess.checkKnight(board,  'n',  x,  y))
							return 'n';
						
						if (Chess.checkQueen(board,  'q',  x,  y))
							return 'q';
						
						if (Chess.checkBishop(board,  'b',  x,  y))
							return 'b';
					
						if (Chess.checkRook(board,  'r',  x,  y))
							return 'r';
						
						
						if (Chess.checkKing(board,  'k',  x,  y))
							return 'k';
						
					}		
				}				
			}
		
		return '-';
		
	}
	


	
	/**
	 * find if pawn can attack king, true if king is in range
	 * @param board - the 8x8 chess board given
	 * @param c - character of piece we are looking for 
	 * @param x - x location of the king
	 * @param y - y location of the king
	 * @return boolean - true or false, false if no piece can attack the king
	 */
	public static boolean checkPawn(String[] board,  char c, int x, int y) {
		// Uppercase means that it is a white piece
		
		if (Character.isUpperCase(c)) {
			
			// This returns true if a white piece is within range of the black king
			if (inBounds(x+1, y+1) && board[y+1].charAt(x+1) == c )
				return true;
			if (inBounds(x-1, y+1) && board[y+1].charAt(x-1) == c )
				return true;
			
		}
		else {
			// black piece in range of white king
			if (inBounds(x+1, y-1) && board[y-1].charAt(x+1) == c )
				return true;
			if (inBounds(x-1, y-1) && board[y-1].charAt(x-1) == c )
				return true;
		}
		return false;
}

	/**
	 * Checks if a knight is in range of the king
	 * 
	 * @param board - the 8x8 chess board given
	 * @param c - character of piece we are looking for 
	 * @param x - x location of the king
	 * @param y - y location of the king
	 * @return boolean - true or false, false if no piece can attack the king
	 */
	public static boolean checkKnight(String[] board, char c, int x, int y) {
		// moves for knight
		// moves in L shape so x = 2, y = 1 means that it moves right 2 and up 1
		// like the king, only has 8 potential moves
		int[] xMove = {2, 2, -2, -2, 1, 1, -1, -1};
		int[] yMove = { 1, -1, 1, -1, 2, -2, 2, -2 };
		
		for (int i = 0; i < 8; i++) {
			
			int tempX = x + xMove[i];
			int tempY = y + yMove[i];
			
			if (inBounds(tempX, tempY) && board[tempY].charAt(tempX) == c)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if bishop is in range of the king
	 * 
	 * @param board - the 8x8 chess board given
	 * @param c - character of piece we are looking for 
	 * @param x - x location of the king
	 * @param y - y location of the king
	 * @return boolean - true or false, false if no piece can attack the king
	 */
	public static boolean checkBishop(String[] board,  char c, int x, int y) {
//		System.out.println("Made it to bishop func");
		
		int count = 0;
		// going bottom right
		while (inBounds(x + count, y + count)) {
			
			if (board[y + count].charAt(x + count) == c) {
				return true;
			}
			count++;
			

		}
		
		count = 0;
		// going bottom left
		while (inBounds(x - count, y + count)) {
			if (board[y + count].charAt(x - count) == c) {
				return true;
			}
			count++;

		}

		count = 0;
		// top left
		while (inBounds(x - count, y - count)) {
			if (board[y - count].charAt(x - count) == c) {
				return true;
			}
			count++;

		}
		count = 0;
		// top right
		while (inBounds(x + count, y - count)) {

			if (board[y - count].charAt(x + count) == c) {
				return true;
			}
			count++;

		}
		
		
		return false;
	}

	
	/**
	 * Checks if the Rook is in range of the king
	 * 
	 * @param board - the 8x8 chess board given
	 * @param c - character of piece we are looking for 
	 * @param x - x location of the king
	 * @param y - y location of the king
	 * @return boolean - true or false, false if no piece can attack the king
	 */
	public static boolean checkRook(String[] board, char c, int x, int y) {
		// basically same technique as bishop

		
		// right
		int count = 0;
		while (inBounds(x + count, y)) {
			if (board[y].charAt(x + count) == c) {
				return true;
			}
			count++;
		}
		
		
		// left
		count = 0;
		while (inBounds(x - count, y)) {
			if (board[y].charAt(x - count) == c) {
				return true;
			}
			count++;
		}

		
		
		//down
		count = 0;
		while (inBounds(x , y + count)) {
			if (board[y + count].charAt(x) == c) {
				return true;
			}
			count++;
		}

		
		// up
		count = 0;
		while (inBounds(x, y - count )) {
			if (board[y - count].charAt(x) == c) {
				return true;
			}
			count++;
		}

		return false;
	}
	
	/**
	 * Checks if the queen is in range of the king
	 * 
	 * @param board - the 8x8 chess board given
	 * @param c - character of piece we are looking for 
	 * @param x - x location of the king
	 * @param y - y location of the king
	 * @return boolean - true or false, false if no piece can attack the king
	 */
	public static boolean checkQueen(String[] board,  char c, int x, int y) {
		// queen has moves of both the Rook and the Bishop
		// Just going to  call those functions 
		
		if (Chess.checkRook(board,  c,  x,  y)) {
			return true;	
		}
		
		if (Chess.checkBishop(board,  c,  x,  y)) {
			return true;	
		}
		
		return false;
	}
	
	/**
	 * Checks if the enemy king is in range of the king
	 * 
	 * @param board - the 8x8 chess board given
	 * @param c - character of piece we are looking for 
	 * @param x - x location of the king
	 * @param y - y location of the king
	 * @return boolean - true or false, false if no piece can attack the king
	 */
	public static boolean checkKing(String[] board,  char c, int x, int y) {
		// Pretty sure i don't need this function for the assignment but going to do it either way
		// This one is easier to do similarly to the knight by writing each of his potential moves
		
		// moves for king
		// he goes in small square, 8 potential moves only
		int[] xMove = {1, 1, 1, 0, 0, -1, -1, -1};
		int[] yMove = {1, 0, -1, 1, -1, 1, 0, -1};
		
		for (int i = 0; i < 8; i++) {
			
			int tempX = x + xMove[i];
			int tempY = y + yMove[i];
			
			if (inBounds(tempX, tempY) && board[tempY].charAt(tempX) == c)
				return true;
		}
		
		return false;
	}
	
	
	public static boolean inBounds(int x, int y) {
		// In bounds if x < 8, x >=0   y < 8, y >=0
		// Added this function purely because it was a pain to type every time
		return x >= 0 && x < 8 && y >= 0 && y < 8;

		

	}
	
	/**
	 * Writes a temporary chess board to a file
	 * Useful for testing
	 * 
	 * @return file - chess board file
	 * @throws FileNotFoundException - if no file is found
	 */
	public static File writeFile() throws FileNotFoundException {
		File myObj = new File("board.txt");
		try (var write = new PrintWriter(myObj )) {
			String[] board = {
					"rnbqkbnr",
					"pppppppp",
					"........",
					"........",
					"........",
					"........",
					"PPPPPPPP",
					"RNBQKBNR" 
			};
			for (var s : board) {
				write.println( s );
			}
		}
		return myObj;

		
		
	}
	
	

	
	
	/**
	 * Main function - useful for extra testing
	 * 
	 * @param args - no args
	 * @throws FileNotFoundException - no file found exception
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Result: " + Chess.go(Chess.writeFile()));


	}

}


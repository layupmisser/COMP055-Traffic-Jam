import java.util.*;


public class Level {	
	private Board board;
	
	private int nRows;
	private int nCols;
	
	private Location winLocation;
	private int numMoves;
		
	//TODO fill out this class with a Level constructor
	//all the other methods necessary and any other instance variables needed
	public Level(int nRows, int nCols) {
		this.nRows = nRows;
		this.nCols = nCols;	
		
		winLocation = new Location(nRows-1,nCols-1);
		setupLevel(nRows, nCols);

		board.addVehicle(VehicleType.MYCAR, 1, 0, false, 2);
		board.addVehicle(VehicleType.TRUCK, 0, 2, true, 3);
		board.addVehicle(VehicleType.AUTO, 3, 3, true, 2);
		board.addVehicle(VehicleType.AUTO, 0, 3, true, 2);	
	}
	
	public int getNumMoves() {
		return numMoves;
	}
	
	public void incrementMoves() {
		numMoves++;
	}
	
	public void setupLevel(int maxRows, int maxCols) {
		// Creates the board with the specified length
		board = new Board(maxRows, maxCols);
	}
	
	public Vehicle getVehicleAt(Location loc) {
		return board.getVehicleAt(loc);
	}
	
	/**
	 * @return the number of columns on the board
	 */
	public int getColumns() {
		//TODO: have this return the number of columns in the level
		return nCols;
	}
	
	public int getRows() {
		return nRows;
	}
	
	public Location getWinLocation() {
		return winLocation;
	}
	
	public boolean moveVehicleAt(Location loc, int numSpaces) {
		return board.moveVehicleAt(loc, numSpaces);
	}
	
	public boolean passedLevel() {
		// Implement code
		
		Location winColumn;
		
		for (int i = 0; i < nCols; i++) {
			winColumn = new Location(i,winLocation.getCol()); // Win location is (nRows - 1, nCols - 1)
			if (board.getVehicleAt(winColumn) != null) {
				if (board.getVehicleAt(winColumn).getVehicleType() == VehicleType.MYCAR) {
					return true;
				}
			}
		}
		return false;
	}


	public ArrayList<Vehicle> getVehiclesOnLevel() {
		return board.getAllVehicles();
	}
	
	//Methods already defined for you
	/**
	 * generates the string representation of the level, including the row and column headers to make it look like
	 * a table
	 * 
	 * @return the string representation
	 */
	public String toString() {
		String result = generateColHeader(getColumns());
		result+=addRowHeader(board.toString());
		return result;
	}
	
	/**
	 * This method will add the row information
	 * needed to the board and is used by the toString method
	 * 
	 * @param origBoard the original board without the header information
	 * @return the board with the header information
	 */
	private String addRowHeader(String origBoard) {
		String result = "";
		String[] elems = origBoard.split("\n");
		for(int i = 0; i < elems.length; i++) {
			result += (char)('A' + i) + "|" + elems[i] + "\n"; 
		}
		return result;
	}
	
	/**
	 * This one is responsible for making the row of column numbers at the top and is used by the toString method
	 * 
	 * @param cols the number of columns in the board
	 * @return if the # of columns is five then it would return "12345\n-----\n"
	 */
	private String generateColHeader(int cols) {
		String result = "  ";
		for(int i = 1; i <= cols; i++) {
			result+=i;
		}
		result+="\n  ";
		for(int i = 0; i < cols; i++) {
			result+="-";
		}
		result+="\n";
		return result;
	}
}

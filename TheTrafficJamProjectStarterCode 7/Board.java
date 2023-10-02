import java.util.*;

/**
 * This represents the board.  Really what it is going to do is just have a 2d array of the vehicles
 * (which we'll refer to as grid), and it will be in charge of doing the bounds type checking for doing any of the moves.
 * It will also have a method display board which will give back a string representation of the board
 * 
 * @author Osvaldo
 *
 */

public class Board {
	Vehicle[][] grid;

	
	//TODO Add the other methods that are in the handout, and fill out the rest of this file
	
	/**
	 * Constructor for the board which sets up an empty grid of size rows by columns
	 * Use the first array index as the rows and the second index as the columns
	 * 
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 */
	
	
	ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	
	private int rows = 0;
	private int cols = 0;
	
	public Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		// Grid initialized with row and col parameters
		grid = new Vehicle[rows][cols];
		//TODO finish implementing this constructor
	}
	
	/**
	 * @return number of columns the board has
	 */
	public int getNumCols() {
		return cols;
	}

	/**
	 * @return number of rows the board has
	 */
	public int getNumRows() {
		return rows;
	}
	
	/**
	 * Grabs the vehicle present on a particular space if any is there
	 * If a Vehicle occupies three spaces, the same Vehicle pointer should be returned for all three spaces
	 * 
	 * @param s the desired space where you want to look to see if a vehicle is there
	 * @return a pointer to the Vehicle object present on that space, if no Vehicle is present, null is returned
	 */
	public Vehicle getVehicleAt(Location s) {

		int locationRow = s.getRow();
		int locationCol = s.getCol();
		
		return grid[locationRow][locationCol];
	}

	/**
	 * adds a vehicle to the board. It would be good to do some checks for a legal placement here.
	 * 
	 * @param type type of the vehicle
	 * @param startRow row for location of vehicle's top
	 * @param startCol column for for location of vehicle leftmost space
	 * @param vert true if the vehicle should be vertical
	 * @param length number of spaces the vehicle occupies on the board
	 */
	public void addVehicle(VehicleType type, int startRow, int startCol, boolean vert, int length) {
		//TODO implement this method, which should addAVehicle to the grid
		
		// Vehicle object created to be added into the grid
		
//		// Test code
//		if (type == VehicleType.MYCAR) {
//			System.out.println("I'm a car.");
//		}
//		else if (type == VehicleType.TRUCK) {
//			System.out.println("I'm a truck.");
//		}
//		else {
//			System.out.println("I'm an auto.");
//		}
			
		Vehicle newVehicle = new Vehicle(type, startRow, startCol, vert, length);
		vehicleList.add(newVehicle);
		
		// Locations of the newly created vehicle obtained in order to access
		// the corresponding grid coordinates
		Location[] vehicleLocation = newVehicle.locationsOn();
		
		int gridRow = 0;
		int gridCol = 0;
		
		// Loop traverses through array given by locationsOn() from vehicle object
		// Assigns each grid element, according to the Locations, with the vehicle object
		for (int i = 0; i < vehicleLocation.length; i++) {
			gridRow = vehicleLocation[i].getRow();
			gridCol = vehicleLocation[i].getCol();
			
			grid[gridRow][gridCol] = newVehicle;
		}
	}
	
	public ArrayList<Vehicle> getAllVehicles() {
		return vehicleList;
	}

	/**
	 * This method moves the vehicle at a certain location a specific number of spaces and updates the board's grid to reflect it
	 * 
	 * @param start the starting location of the vehicle in question
	 * @param numSpaces the number of spaces to be moved by the vehicle (can be positive or negative)
	 * @return whether or not the move actually happened
	 */
	public boolean moveVehicleAt(Location start, int numSpaces) {
		//TODO change this method to implementing moving a vehicle that is on a certain row/column a certain number of spaces
		
		// sVehicle = selected vehicle
		Vehicle sVehicle = getVehicleAt(start);
		boolean canMove = canMoveAVehicleAt(start,numSpaces);

		Location[] currVehicleLocation = sVehicle.locationsOn();
		Location[] newVehicleLocation;
		
		int gridRow = 0;
		int gridCol = 0;
		
		// If canMoveAVehicle() says that the numSpaces moved is good:
		// Vehicle is moved on the grid
		if (canMove) {
			
			// Clears the current spaces a vehicle occupies on grid
			// Updates grid with new location vehicle now occupies
			for (int i = 0; i < currVehicleLocation.length; i++) {
				// Clears currently occupied spaces
				
				
				gridRow = currVehicleLocation[i].getRow();
				gridCol = currVehicleLocation[i].getCol();
			
				// Print Test Code
//				System.out.println("Current vehicle location: r" + gridRow + "c" + gridCol);

				
				grid[gridRow][gridCol] = null;
			}
			
			sVehicle.move(numSpaces);
			newVehicleLocation = sVehicle.locationsOn();
			
			for (int i = 0; i < newVehicleLocation.length; i++) {
				// Clears currently occupied spaces
				
				// Occupies new spaces
				gridRow = newVehicleLocation[i].getRow();
				gridCol = newVehicleLocation[i].getCol();
				
				// Print Test Code		
//				System.out.println("New vehicle location: r" + gridRow + "c" + gridCol);

				grid[gridRow][gridCol] = sVehicle;
			}
			

			
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method just checks to see if the vehicle on a certain location can move a specific number of spaces, though
	 * it will not move the vehicle.  You should use this when you wish to move or want to see if you can
	 * move a vehicle numSpaces without going out of bounds or hitting another vehicle
	 * 
	 * @param start the starting row/column of the vehicle in question
	 * @param numSpaces number of spaces to be moved by the vehicle (positive or negative)
	 * @return whether or not the move is possible
	 */
	public boolean canMoveAVehicleAt(Location start, int numSpaces) {
		
		
		Vehicle sVehicle = getVehicleAt(start);
		
		if (sVehicle == null) {
			// DELETE TEST
//			System.out.println("No vehicle is at that location.");
			return false;
		}
		
		// Print test code
//		System.out.println("Vehicle Type is: " + sVehicle.getVehicleType());
		
		// Obtains array of locations vehicle would end up on
		// given the numSpaces
		Location[] vehicleLocation = sVehicle.locationsPath(numSpaces);
		
		// Variables temporarily store the row and col location of selected
		// vehicle
		int sRow = 0;
		int sCol = 0;
		
		// Traverses the location array from behind and returns false
		// if any row or col coordinates result in out of bounds
		for (int i = vehicleLocation.length-1; i >= 0; i--) {
			sRow = vehicleLocation[i].getRow();
			sCol = vehicleLocation[i].getCol();
			
			// DELETE TEMP
//			System.out.println("sRow: " + sRow);
//			System.out.println("sCol: " + sCol);
			
			// Checks if coordinates are inside the bounds
			if (((sRow >= this.rows) || (sRow < 0)) || ((sCol >= this.cols) || (sCol < 0))) {
				
				
				// DELETE TEMP
//				System.out.println("locationsPath() out of bounds.");
				return false;
			}
			// Checks if coordinates    are not already taken by another vehicle
			if ((grid[sRow][sCol] != sVehicle) && (grid[sRow][sCol] != null)) {
				
				// DELETE TEMP
//				System.out.println("locationsPath() vehicle collision.");
				return false;
			}
		}
		
		return true;
	}
	
	// This method helps create a string version of the board
	// You do not need to call this at all, just let it be
	public String toString() {
		return BoardConverter.createString(this);
	}
	
	/* Testing methods down here for testing the board 
	 * make sure you run the board and it works before you write the rest of the program! */
	
	public static void main(String[] args) {
		Board b = new Board(5, 5);
		b.addVehicle(VehicleType.MYCAR, 1, 0, false, 2);
		b.addVehicle(VehicleType.TRUCK, 0, 2, true, 3);
		b.addVehicle(VehicleType.AUTO, 3, 3, true, 2);
		b.addVehicle(VehicleType.AUTO, 0, 3, true, 2);
		System.out.println(b);
		testCanMove(b);
		testMoving(b);
		System.out.println(b);
	}
	
	public static void testMoving(Board b) {
		System.out.println("just moving some stuff around");
		b.moveVehicleAt(new Location(1, 2), 1);
		b.moveVehicleAt(new Location(1, 2), 1);
		b.moveVehicleAt(new Location(1, 1), 1);
	}
	
	public static void testCanMove(Board b) {
		System.out.println("Ok, now testing some moves...");
		System.out.println("These should all be true");
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(0, 2), 2));
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(1, 2), 2));
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(2, 2), 2));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(3, 3), -1));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(4, 3), -1));
		
		System.out.println("\nAnd these should all be false");
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(3, 2), 2));
		System.out.println("Moving the car into truck " + b.canMoveAVehicleAt(new Location(1, 0), 1));
		System.out.println("Moving the car into truck " + b.canMoveAVehicleAt(new Location(1, 0), 2));
		System.out.println("Moving nothing at all " + b.canMoveAVehicleAt(new Location(4, 4), -1));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(3, 3), -2));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(4, 3), -2));
		System.out.println("Moving upper auto up " + b.canMoveAVehicleAt(new Location(0, 3), -1));
		System.out.println("Moving upper auto up " + b.canMoveAVehicleAt(new Location(1, 3), -1));
		
		// Additional test code
		
		// Testing: access a vehicle from a certain Location & checking to see if it occupies certain location
		System.out.println("\nAttempting to access a location (4,0) with NO vehicle. The result below should be null as in no vehicle found: ");
		System.out.println(b.getVehicleAt(new Location(4,0)) + "\n");
		
		System.out.println("Attempting to access a location (2,3) with NO vehicle. The result below should be null as in no vehicle found: ");
		System.out.println(b.getVehicleAt(new Location(2,3)) + "\n");
		
		System.out.println("Attempting to access a location (1,0) WITH a car vehicle. The result below should be a memory address of a vehicle object: ");
		System.out.println(b.getVehicleAt(new Location(1,0)) + "\n");
		
		System.out.println("Attempting to access a location (1,2) WITH a truck vehicle. The result below should be a memory address of a vehicle object: ");
		System.out.println(b.getVehicleAt(new Location(1,2)) + "\n");
		
		// Testing: testing to see if vehicle is in the way
		System.out.println("Attempting to move horiz car at location (1,1) 1 space. Below should be false, because the vehicle is blocked by another vehicle:");
		System.out.println(b.canMoveAVehicleAt(new Location(1,1), 1) + "\n");
		
		System.out.println("Attempting to move vert car at location (1,3) 1 space. Below should be true, because vehicle in the way to block it:");
		System.out.println(b.canMoveAVehicleAt(new Location(1,3), 1) + "\n");
		
		System.out.println("Attempting to move vert car at location (1,3) 2 spaces. Below should be false, because there is a vehicle in the way to block it:");
		System.out.println(b.canMoveAVehicleAt(new Location(1,3), 2) + "\n");
		
		System.out.println("Attempting to move vert car at location (3,3) -2 spaces. Below should be false, because there is a vehicle in the way to block it:");
		System.out.println(b.canMoveAVehicleAt(new Location(3,3), -2) + "\n");
		
	}
}

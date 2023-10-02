public class Vehicle {
	// TODO You'll need to fill in this entire file

	/**
	 * @return the type associated with this particular vehicle
	 */
	
	
	int vehicleType;
	int sRow;
	int sCol;
	int length;
	
	boolean isVertical;
	
	VehicleType type;
	
	/* 1 = MYCAR
	 * 2 = AUTO
	 * 3 = TRUCK
	 */
	
	public Vehicle(VehicleType type, int sRow, int sCol, boolean isVertical, int length) {
		
		this.type = type;
		this.sRow = sRow;
		this.sCol = sCol;
		this.isVertical = isVertical;
		this.length = length;
	}
	
	public VehicleType getVehicleType() {
		// TODO change this implementation so that you return the vehicles
		// actual type, which should be stored in a variable
		// . Right now it only returns the type mycar
				
		return type;
		
	}

	/**
	 * Provides an array of Locations that indicate where a particular Vehicle
	 * would be on top of, calculated from the Vehicle's starting space (see narration)
	 * 
	 * @return the array of Spaces occupied by that particular Vehicles
	 */
	public Location[] locationsOn() {
		
		int tempRow = sRow;
		int tempCol = sCol;
		
		Location location[] = new Location[length];
		for (int i = 0; i < length; i++) {
			
			Location newLocation = new Location(tempRow,tempCol);
			
			location[i] = newLocation;
			
			if (isVertical) {
				tempRow++;
			}
			else {
				tempCol++;
			}
		}
		
		return location;
	}

	/**
	 * Calculates an array of the locations that would be traveled if a vehicle
	 * were to move a certain number of path, which represents the path taken
	 * 
	 * @param numSpaces
	 *            The number of spaces to move (can be negative or positive)
	 * @return The array of Locations that would need to be checked for Vehicles
	 */
	public Location[] locationsPath(int numSpaces) {

		int tempRow = sRow;
		int tempCol = sCol;
		
		// Calculates the end coordinate of the vehicle
		Location location[] = new Location[Math.abs(numSpaces)];
		
		if (numSpaces > 0) {
			if (isVertical) {
				tempRow += length-1;
			}
			else {
				tempCol += length-1;
			}
		}
			
		for (int i = 0; i < Math.abs(numSpaces); i++) {
			
			if (numSpaces > 0) {
				if (isVertical) {
					tempRow++;
				}
				else {
					tempCol++;
				}
			}
			else {
				if (isVertical) {
					tempRow--;
				}
				else {
					tempCol--;
				}
			}
			
//			System.out.println("i = " + i + ", tempRow = " + tempRow + ", tempCol" + tempCol);
			
			Location newLocation = new Location(tempRow,tempCol);
			
			location[i] = newLocation;
		}
		
//		}
//		else {
//			
//			for (int i = Math.abs(numSpaces)-1; i >= 0; i--) {
//				
//				if (isVertical) {
//					tempRow--;
//				}
//				else {
//					tempCol--;
//				}
//				
//				Location newLocation = new Location(tempRow,tempCol);
//				
//				location[i] = newLocation;
//			
//			}
//		}
		
		return location;
	}

	public void move(int numSpaces) {
		
		if (isVertical) {
			sRow += numSpaces;
		}
		else {
			sCol += numSpaces;
		}
	}
	
	public Location potentialMove(int numSpaces) {		
		
		int tempRow = sRow;
		int tempCol = sCol;
		
		
		if (isVertical) {
			tempRow += numSpaces;
		}
		else {
			tempCol += numSpaces;
		}
		
		Location newLocation = new Location(tempRow,tempCol);

		return newLocation;
	}
	
	// prints out more legibly the row & columns for an array of locations   
	public static void printLocations(Location[] arr) { 
	  for(int i = 0; i < arr.length; i++) { 
		  System.out.print("r" + arr[i].getRow() + "c" + arr[i].getCol() + "; ");  
	  } 
	  System.out.println(); 
	} 
	
	// Continue here ***********
	// https://www.scaler.com/topics/tostring-method-in-java/
	
	public Location getStart() {
		Location sLocation = new Location(sRow, sCol);
		return sLocation;
	}
	
	public void setStartRow(int sRow) {
		this.sRow = sRow;
	}
	
	public void setStartCol(int sCol) {
		this.sCol = sCol;
	}
	
	
	public static void main(String[] args) { 
		Vehicle someTruck = new Vehicle(VehicleType.TRUCK, 1, 1, true, 3); 
		Vehicle someAuto = new Vehicle(VehicleType.AUTO, 2, 2, false, 2); 
		
		System.out.println("This next test is for locationsOn: "); 
		System.out.println("vert truck at r1c1 should give you r1c1; r2c1; r3c1 as the locations its on top of...does it?"); 
		printLocations(someTruck.locationsOn()); 
		
		System.out.println("horiz auto at r2c2 should give you r2c2; r2c3 as the locations its on top of...does it?"); 
		printLocations(someAuto.locationsOn()); 
		
		System.out.println("if we were to move horiz auto r2c2 -2 spaces it should give you at least r2c0; r2c1; it may also add r2c2; r2c3 to its answer...does it?"); 
		printLocations(someAuto.locationsPath(-2)); 
		
		System.out.println("\nAdditional Test Code for locationsPath()"); 
		System.out.println("if we were to move horiz auto (length 2) r2c2 2 spaces it should give you at least r2c4; r2c5; it may also add r2c2; r2c3 to its answer...does it?"); 
		printLocations(someAuto.locationsPath(2)); 
		
		
		Vehicle geoMetro = new Vehicle(VehicleType.AUTO,0,0, false,2);
		System.out.println("if we were to move horiz geoMetro (length 2) r0c0 -4 spaces it should give you r0c-4|r0c-3|r0c-2|r0c-1"); 
		printLocations(geoMetro.locationsPath(-4)); 
		
		System.out.println("if we were to move horiz geoMetro (length 2) r0c0 4 spaces it should give you r0c2|r0c3|r0c4|r0c5"); 
		printLocations(geoMetro.locationsPath(4)); 
		
		Vehicle rv = new Vehicle(VehicleType.AUTO,3,3, true,4);
		System.out.println("if we were to move vert rv (length 4) r3c3 -5 spaces it should give you r-2c3|r-1c3|r0c3|r1c3|r2c3"); 
		printLocations(rv.locationsPath(-5)); 
		
		System.out.println("if we were to move vert rv (length 4) r3c3 5 spaces it should give you r7c3|r8c3|r9c3|r10c3|r11c3"); 
		printLocations(rv.locationsPath(5)); 
		
		
		System.out.println("if we were to move vert truck (length 3) r1c1 3 spaces it should give you at least r2c1; r3c1; r4c1"); 
		printLocations(someTruck.locationsPath(3));
		
		System.out.println("if we were to move vert truck (length 3) r1c1 -3 spaces it should give you at least r0c1; r-1c1; r-2c1"); 
		printLocations(someTruck.locationsPath(-3));
		
		//ADD SOME MOVE AND POTENTIALMOVE TEST CODE BELOW THIS LINE
		
		// Test code for move
		System.out.println("\n------------[Test Code for move()]------------\n");

		// Negative spaces moved horizontally
		System.out.println("\nThe next test will be for move() --> moving negative spaces horizontally:");
		System.out.println("if we were to move horiz auto at r2c2;r2c3 -2 spaces, the coordinates should be r2c0;r2c1 as seen below:");
		someAuto.move(-2);
		printLocations(someAuto.locationsOn());
		
		// Positive spaces moved horizontally
		System.out.println("\nThe next test will be for move() --> moving positive spaces horizontally:");
		System.out.println("if we were to move horiz auto at r2c0;r2c1 2 spaces, the coordinates should be r2c3;r2c4 as seen below:");
		someAuto.move(2);
		printLocations(someAuto.locationsOn());
		
		System.out.println("\nThe next test will be for move() --> moving negative spaces vertically:");
		System.out.println("if we were to move vert truck at r1c1; r2c1; r3c1 -3 spaces, the coordinates should be r-2c1;r-1c1;r0c1 as seen below:");
		someTruck.move(-3);
		printLocations(someTruck.locationsOn());
		
		someTruck.setStartCol(1);
		someTruck.setStartRow(-2);
		System.out.println("\nThe next test will be for move() --> moving positive spaces vertically:");
		System.out.println("if we were to move vert truck at r-2c1 (main position); r-1c1; r0c1 4 spaces, the coordinates should be r2;r3c1;r4c1 as seen below:");
		someTruck.move(4);
		printLocations(someTruck.locationsOn());
		
		// Test code for potential move
		System.out.println("\n---------[Test Code for potentialMove()]----------\n");
		someAuto.setStartRow(2);
		someAuto.setStartCol(3);
		System.out.println("\nThe next test will be for potentialMove() --> moving negative spaces vertically:");
		System.out.println("if we were to move horiz auto at r2c3 -2 spaces, the potential coordinates should be r2c1 as seen below:");
		System.out.println(someAuto.potentialMove(-2).toString());

		
		System.out.println("\nThe next test will be for potentialMove() --> moving positive spaces horizontally:");
		System.out.println("if we were to move horiz auto at r2c3 3 spaces, the potential coordinates should be r2c6 as seen below:");
		System.out.println(someAuto.potentialMove(3).toString());
		
		someTruck.setStartRow(4);
		someTruck.setStartCol(1);
		System.out.println("\nThe next test will be for potentialMove() --> moving negative spaces vertically:");
		System.out.println("if we were to move vert truck at r4c1 -5 spaces, the potential coordinates should be r-1c1 as seen below:");
		System.out.println(someTruck.potentialMove(-5).toString());
		
		System.out.println("\nThe next test will be for potentialMove() --> moving positive spaces vertically:");
		System.out.println("if we were to move vert truck at r4c1 4 spaces, the potential coordinates should be r8c1 as seen below:");
		System.out.println(someTruck.potentialMove(4).toString());
		
		
		
	} 
}

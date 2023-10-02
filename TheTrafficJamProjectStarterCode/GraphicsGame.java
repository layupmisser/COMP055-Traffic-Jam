import acm.program.*;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import java.util.*;

public class GraphicsGame extends GraphicsProgram {
	
	/**
	 * Here are all of the constants
	 */
	
	
	// Win Label Settings
	private static final String WIN_LABEL_MESSAGE = "Congratulations, you won! Your car reached the end.";	
	private static final String WIN_LABEL_FONT = "Arial-Bold-12";
	private static final Color WIN_LABEL_COLOR = Color.red;
	
	// Adjust these constants to adjust window size
	public static final int PROGRAM_WIDTH = 500;
	public static final int PROGRAM_HEIGHT = 500;

	// Label settings of large text like the win box
	public static final String lABEL_FONT = "Arial-Bold-22";
	private static final Color LABEL_COLOR = Color.red;

	public static final String EXIT_SIGN = "EXIT";
	public static final String IMG_FILENAME_PATH = "images/";
	public static final String IMG_EXTENSION = ".png";
	public static final String VERTICAL_IMG_FILENAME = "_vert";

	
	// TODO declare your instance variables here

	// Change this constant to change the winLocation
	public static final Location winLocation = new Location(2,5); 
	
	// Change these constants to adjust number of rows and columns
	public static final int rows = 6;
	public static final int columns = 6;

	
	
	private Level level;
	
	// Variable storing number of moves label
	private GLabel numMoves;
	// Store object obtained from mouseEvent
	private GObject toDrag;
	
	// Stores mouse event from mouse click event
	private MouseEvent initialMouseEvent;
	

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
	}

	public void run() {
		// TODO write this part, which is like your main function
		String s = "Hello";
		s.charAt(0);
		
		addMouseListeners();
		
		
		level = new Level(rows, columns);
		level.setWinLocation(winLocation);

		drawLevel();
		
	}

	// Creates the level using declared methods
	private void drawLevel() {
		// TODO write the code to draw the entire level, which should
		// mostly be calls to some of your helper functions.
		
		// Cars are drawn from a list of cars found in level.java
		drawCars();
		drawGridLines();
		drawWinningTile();
		drawNumberMoves();
		
	}

	/**
	 * This should draw the label EXIT and add it to the space that represents
	 * the winning tile.
	 */
	private void drawWinningTile() {
		
		// Creates a label indicating the win location based on the Location
		
		Location winLocation = level.getWinLocation();
		
		// Rows are Y, size of them is height
		// Cols are X, size of them is width
		
		int winLocationY = (int) (winLocation.getRow() * cellHeight());
		int winLocationX = (int) (winLocation.getCol() * cellWidth());
		
		
		GLabel label = new GLabel("EXIT", winLocationX, winLocationY);
		label.setFont(lABEL_FONT);
		label.setColor(LABEL_COLOR);
		label.setLocation(winLocationX, winLocationY+label.getHeight());
		
		add(label);
	}

	/**
	 * draw the lines of the grid. Test this out and make sure you have it
	 * working first. Should draw the number of grids based on the number of
	 * rows and columsn in Level
	 */
	private void drawGridLines() {
		
		// Starting position of the lines, each are incremented
		int X1, Y1, X2, Y2;
		X1 = X2 = Y1 = Y2 = 0;

		// Number of grid lines: numRows - 1
		// Y distance between rows is represented by cellHeight()

		// Grid row lines added first
		for (int i = 0; i < rows; ++i) {
			
			// X1 remains 0
			X2 = PROGRAM_WIDTH;
			
			Y1 += cellHeight();
			Y2 += cellHeight();
			
			// Line is created from (x1,y1) to (x2,y2)
			// 						(0, cellHeight),(PROGRAM_WIDTH,cellHeight)
			
			GLine line = new GLine(X1, Y1, X2, Y2);
			add(line);
		}
		
		X1 = X2 = Y1 = Y2 = 0;
		
		// Grid column lines added
		for (int i = 0; i < columns; ++i) {
			
			// Y1 remains 0
			Y2 = PROGRAM_HEIGHT;
			X1 += cellWidth();
			X2 += cellWidth();
			
			// Line is created from (x1,y1) to (x2,y2)
			// 						(cellWidth,0),(cellWidth,PROGRAM_HEIGHT)
						
			
			GLine line = new GLine(X1, Y1, X2, Y2);
			add(line);
		}
		
	}

	/**
	 * Maybe given a list of all the cars, you can go through them and call
	 * drawCar on each?
	 */
	private void drawCars() {	
		// Cars are drawn from a list of cars found in level.java
		for (Vehicle v : level.getVehiclesOnLevel()) {
			drawCar(v);
		}
	}

	private void drawNumberMoves() {
		// Creates a label on the board with the number of moves
		
		
		// Removes current number of moves text if it exists
		if (numMoves != null) {
			remove(numMoves);
		}
		
		// Declares new number of move text
		numMoves = new GLabel("" + level.getNumMoves(),0,cellHeight());
		numMoves.setFont(lABEL_FONT);
		add(numMoves);
	}
	
	/**
	 * Given a vehicle object, which we will call v, use the information from
	 * that vehicle to then create a GImage and add it to the screen. Make sure
	 * to use the constants for the image path ("/images"), the extension ".png"
	 * and the additional suffix to the filename if the object is vertical when
	 * creating your GImage. Also make sure to set the images size according to
	 * the size of your spaces
	 * 
	 * @param v
	 *            the Vehicle object to be drawn
	 */
	private void drawCar(Vehicle v) {

		Location vStartingLoc = v.getStart();
		
		// The x and y locations of image = calculated based on provided location * cell dimensions
		int startingY = (int) (vStartingLoc.getRow() * cellHeight());
		int startingX = (int) (vStartingLoc.getCol() * cellWidth());
		int vehicleLength = v.getLength();	// Vehicle length taken into account
		// If vertical, vehicle's Y image value is multiplied by its length
		// If horizontal, vehicle's X image value is multiplied by its length

		
		GImage carImage;
		String vehicleType = v.getVehicleType().toString();
		
		
		// Draws out a car according to it being vertical or horizontal
		if (v.isVertical()) {
			
			// The vehicle is vertical
			
			carImage = new GImage(IMG_FILENAME_PATH+vehicleType+VERTICAL_IMG_FILENAME+IMG_EXTENSION,startingX, startingY);
			carImage.setSize(cellWidth(),cellHeight()*vehicleLength);
		}
		else {
			
			// The vehicle is horizontal
			
			carImage = new GImage(IMG_FILENAME_PATH+vehicleType+IMG_EXTENSION,startingX, startingY);
			carImage.setSize(cellWidth()*vehicleLength,cellHeight());
		}
		
		add(carImage);
		
	}

	// TODO implement the mouse listeners here

	/**
	 * Given a xy coordinates, return the Vehicle that is currently at those x
	 * and y coordinates, returning null if no Vehicle currently sits at those
	 * coordinates.
	 * 
	 * @param x
	 *            the x coordinate in pixels
	 * @param y
	 *            the y coordinate in pixels
	 * @return the Vehicle object that currently sits at that xy location
	 */
	private Vehicle getVehicleFromXY(double x, double y) {
		// Obtains a list of vehicles on the board from level from board
		return level.getVehicleAt(convertXYToLocation(x,y));
	}

	/**
	 * This is a useful helper function to help you calculate the number of
	 * spaces that a vehicle moved while dragging so that you can then send that
	 * information over as numSpacesMoved to that particular Vehicle object.
	 * 
	 * @return the number of spaces that were moved
	 */
	private int calculateNumSpacesMoved(Location startLocation, Location endLocation) {

		Vehicle selectedVehicle = level.getVehicleAt(startLocation);

		int endCol = endLocation.getCol();
		int endRow = endLocation.getRow();
		
		// Vehicle's starting location is obtained because it is always consistent no matter
		// the cell the user clicked to receive the vehicle from
		int startCol = selectedVehicle.getStart().getCol();
		int startRow = selectedVehicle.getStart().getRow();
		int numSpaces = 0;
		
		System.out.println("Calculated Num Space eCol: " + endCol);
		System.out.println("Calculated Num Space eRow: " + endRow);
		System.out.println("Calculated Num Space sCol: " + startCol);
		System.out.println("Calculated Num Space sRow: " + startRow);
		
		
		// Simple calculation where endLocation is subtracted with startLocation to receive
		// the num of spaces
		if (selectedVehicle.isVertical()) {
			numSpaces = endRow - startRow;
		}
		else  {
			numSpaces = endCol - startCol;
		}
		
		// Accounts for the length of a vehicle
		// Otherwise, numSpaces calculation might be thrown off if the user clicked on various
		// parts of the vehicle
		if (numSpaces > 0) {
			numSpaces -= selectedVehicle.getLength()-1;
		}
		return numSpaces;
			
	}

	/**
	 * Another helper function/method meant to return the location given an x and y
	 * coordinate system. Use this to help you write getVehicleFromXY
	 * 
	 * @param x
	 *            x-coordinate (in pixels)
	 * @param y
	 *            y-coordinate (in pixels)
	 * @return the Location associated with that x and y
	 */
	private Location convertXYToLocation(double x, double y) {
		// TODO write this implementation hint (use helper methods below)
		
		int locX = (int)(x/cellWidth());
		int locY = (int)(y/cellHeight());
		
		return new Location(locY, locX);
	}

	/**
	 * 
	 * @return the width (in pixels) of a single cell in the grid
	 */
	private double cellWidth() {
		// TODO fix this method
		return PROGRAM_WIDTH/columns;
	}

	/**
	 * 
	 * @return the height in pixels of a single cell in the grid
	 */
	private double cellHeight() {
		// TODO fix this method
		return PROGRAM_WIDTH/rows;
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		
//		Location trueLocation = convertXYToLocation(e.getX(),e.getY());
//		
//		int trueX = trueLocation.getCol();
//		int trueY = trueLocation.getRow();

		// Obtains object at mouse location when mouseClick
		toDrag = getElementAt(e.getX(),e.getY());
		
		// Prevents anything but a GImage from being selected
		if (!(toDrag instanceof GImage)) return;
		
		initialMouseEvent = e;
		//code to tell the computer what to do when the mouse is pressed
	
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		// The location of where the mouse cursor ended
		// Where the user stopped dragging a vehicle
		
		int numSpaces = 0;
		Vehicle selectedVehicle;
		Vehicle movedVehicle;
		
		Location startLocation;
		Location endLocation;

		// Prevents anything but a GImage from being selected
		if (!(toDrag instanceof GImage)) return;

		// Branch executes only if mouseClick clicked on a GImage
		if (toDrag != null) {
		
			// End location and start location calculated for calculateNumSpacesMoved
			endLocation = convertXYToLocation(e.getX(),e.getY());	
			startLocation = convertXYToLocation(initialMouseEvent.getX(), initialMouseEvent.getY());
			
			// Test Code for the above
			System.out.println("Start row: " + startLocation.getRow());
			System.out.println("Start col: " + startLocation.getCol());
			
			System.out.println("End row: " + endLocation.getRow());
			System.out.println("End col: " + endLocation.getCol());
			
			selectedVehicle = getVehicleFromXY(initialMouseEvent.getX(),initialMouseEvent.getY());
			numSpaces = calculateNumSpacesMoved(startLocation, endLocation);			
			System.out.println("Numspaces: " + numSpaces);

			// Branch executes if numSpaces to move is valid
			// Will not execute if: another vehicle is in the way given numSpaces
			// Will not execute if: the vehicle will end up out of bounds of the board given numSpaces
			if (level.moveVehicleAt(startLocation, numSpaces) && numSpaces != 0) {
				level.incrementMoves();	// Number of moves incremented only upon successful move
				System.out.println("Vehicle successfully moved " + numSpaces + " space(s).");

				
				// Test code to verify that vehicle was selected and moved properly
				movedVehicle = level.getVehicleAt(endLocation);
				
				if (movedVehicle != null) {
					System.out.println("The selected vehicle was a: " + selectedVehicle.getVehicleType());
					System.out.println("The selected vehicle's address was: " + selectedVehicle);
					System.out.println("The vehicle that was actually moved was a: " + movedVehicle.getVehicleType());
					System.out.println("The moved vehicle's address was: " + movedVehicle);
				}
				
				if (selectedVehicle == movedVehicle) {
					System.out.println("CORRECT: The car clicked on initially WAS the one that was moved.");
				}
				else {
					System.out.println("ERROR: The car clicked on initially was NOT the one that was moved.");
				}
				
				
				// Moves vehicle image
				if (selectedVehicle.isVertical()) {
					toDrag.move(0, numSpaces*cellHeight());
				}	
				else {
					toDrag.move(numSpaces*cellWidth(),0);
				}	
				
				drawNumberMoves();
				
				// Checks to see if car has made it
				if (level.passedLevel()) {
					removeAll();
					
					GLabel label = new GLabel(WIN_LABEL_MESSAGE + " It took you " + level.getNumMoves() + " moves.", 0, 0);
					label.setFont(WIN_LABEL_FONT);
					label.setColor(WIN_LABEL_COLOR);
					label.move(0, label.getHeight());
					add(label);
				}
			}
			else {
				System.out.println("Sorry, but that vehicle can't be moved to where you want. Please try again.");
			}			
			
			
			System.out.println();			
		}
		
	
		
	}
	
	public static void main(String[] args) {
		new GraphicsGame().start();
	}
}

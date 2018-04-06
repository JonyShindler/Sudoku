package logic;

import java.util.List;

import logic.GridAccessService.GridPosition;

/**
 * 
 * Service to determine if a proposed value is valid
 * @author Jony
 *
 */
public class ValidValueService {
	private final GridAccessService gridServices;
	
	//Constructor
	public ValidValueService(GridAccessService gridServices){
		this.gridServices = gridServices;
	}
	
	/**
	 * @param proposedValue the number (1to9) proposed to go in the grid
	 * @param xValue the x value of the grid position
	 * @param yValue the y value of the grid positon
	 * @param grid the grid to query against
	 * @return true is the proposed value is valid
	 */
	public boolean isValid(int proposedValue, int xValue, int yValue, int[][] grid){
		return isRowValid(proposedValue, xValue, yValue, grid) && isColumnValid(proposedValue, xValue, yValue, grid) && isBoxValid(proposedValue, xValue, yValue, grid);
	}

	
	/**
	 * @param xValue the x value of the grid
	 * @param yValue the y value of the grid
	 * @param grid the grid to query over
	 * @return true if the grid value is empty for the current position
	 */
	public boolean isEmpty(int xValue, int yValue, int[][] grid){
		return grid[yValue][xValue]==0;
	}
	
	
	/**
	 * @param grid
	 * @return true if grid is full (i.e. it doesnt contain any zeros) then return true. If it contains any zeros in it then the sudoku will be solved.
	 * 
	 */
	public static boolean isGridFull(int[][] grid){
		for (int i = 0; i<=8; i++){
			for (int j=0; j<=8; j++){
				if (grid[i][j]==0){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param proposedValue
	 * @param xValue
	 * @param yValue
	 * @param grid
	 * @return true if the proposed value is valid based on the current row
	 */
	private boolean isRowValid(int proposedValue, int xValue, int yValue, int[][] grid){
		
		int row[] = grid[yValue];
		int i=0;
		
		for (i=0; i<9; i++){
			if (row[i]==proposedValue){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * @param proposedValue
	 * @param xValue
	 * @param yValue
	 * @param grid
	 * @return true if the propsed value is valid based on the current column
	 */
	private boolean isColumnValid(int proposedValue, int xValue, int yValue, int[][] grid){
		
		//for each row, take the first value and store it in the column
		//we will defintely make a service to do this properly
		//also get some Junit tests going as shit is gonna start getting harder soon.
		//instead of making a service just make a grid class with getters and setters for rows and columns
		
		int column[] = new int[9];
		
		for (int i=0;i<9;i++){
			column[i]=grid[i][xValue];
		}
		
		int i=0;
		
		for (i=0; i<9; i++){
			if (column[i]==proposedValue){
				return false;
			}
		}
		return true;
	}

	
	/**
	 * @param proposedValue
	 * @param xValue
	 * @param yValue
	 * @param grid
	 * @return true if the proposed value is valid based on the current box
	 */
	private boolean isBoxValid(int proposedValue, int xValue, int yValue, int[][] grid){

		GridPosition position = new GridPosition(yValue, xValue);
		//work out which box the grid position is in
		int currentBox = gridServices.findBoxCurrentPositionIsIn(position);
		//then find the list of all numbers that are currently in the box
		List<Integer> listInBox = gridServices.findValuesInCurrentBox(currentBox, grid);
		//then return true if the proposed value is valid (i.e. it does not exist in the box)
		return !listInBox.contains(proposedValue);
		
	}
	
	
	/**
	 * Returns the lowest valid number to go in a specified grid position
	 * 
	 * @param i
	 * @param j
	 * @param proposedValue
	 * @param valueValidService
	 * @param knownSafeGrid
	 * @param startNumber
	 * @return the lowest valid number to go in a grid position
	 */
	public int findLowestValue(int i, int j, int proposedValue, ValidValueService valueValidService,
			int[][] knownSafeGrid, int startNumber) {
		//work out which is the lowest number that we can out in here.
		for (int x=startNumber; x<=9; x++){
			//if the number is valid then set this as the proposed value
			if (valueValidService.isValid(x, j, i, knownSafeGrid)){
				return proposedValue = x;
			}
		}
		return 0;
	}
	
}

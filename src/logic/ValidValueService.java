package logic;

import java.util.List;
import java.util.Map;

/**
 * 
 * Service to determine if a proposed value is valid
 * @author Jony
 *
 */
public class ValidValueService {

	public static boolean isValid(int proposedValue, 
								  int indexInList, 
								  List<Integer> grid, 
								  SolverType solverType,
								  Map<Integer, Integer> mapOfBoxPositions){
		return isRowValid(proposedValue, indexInList, grid)
			&& isColumnValid(proposedValue, indexInList, grid)
			&& isBoxValid(proposedValue, indexInList, grid, solverType, mapOfBoxPositions);
	}
	
	
	/**
	 * Asserts that a proposed addition to a row is valid
	 * @return true is the proposed value is valid on its row.
	 */
	public static boolean isRowValid(int proposedValue, int indexInList, List<Integer> grid){
		int rowNumber = findRowNumber(indexInList);
		
		for (int i = 1 ; i <= grid.size()-1 ; i++) {
			int currentRowInLoop = findRowNumber(i);
			if (currentRowInLoop == rowNumber
					&& grid.get(i) != null
					&& grid.get(i) == proposedValue) {
				return false;
			}
		}
		return true;
	}


	/**
	 * @return the row number the index appears in, with the 1 to 9 row numbering.
	 */
	public static int findRowNumber(int indexInList) {
		return (int)Math.ceil((indexInList-1)/9) + 1;
	}
	
	
	/**
	 * Asserts that a proposed addition to a column is valid
	 * @return true is the proposed value is valid in its column.
	 */
	public static boolean isColumnValid(int proposedValue, int indexInList, List<Integer> grid){
		int columnNumber = findColumnNumber(indexInList);
		
		for (int i = 1 ; i <= grid.size()-1 ; i++) {
			int currentRowInLoop = findColumnNumber(i);
			if (currentRowInLoop == columnNumber
					&& grid.get(i) != null
					&& grid.get(i) == proposedValue) {
				return false;
			}
		}
		return true;
	}


	/**
	 * @return the column number the index appears in, with the 1 to 9 row numbering.
	 */
	public static int findColumnNumber(int indexInList) {
		int remainder = indexInList % 9;
		return remainder == 0 ? 9 : remainder;
	}
	
	
	public static boolean isBoxValid(int proposedValue, 
									 int indexInList, 
									 List<Integer> grid, 
									 SolverType solverType, 
									 Map<Integer, Integer> mapOfBoxPositions){
		// Work out which box the grid position is in
		int currentBox = GridAccessService.findBoxNumber(indexInList, solverType, mapOfBoxPositions);
		// Then find the list of all numbers that are currently in the box
		List<Integer> listInBox = GridAccessService.findValuesInCurrentBox(currentBox, grid, solverType, mapOfBoxPositions);
		// Then return true if the proposed value is valid (i.e. it does not exist in the box)
		return !listInBox.contains(proposedValue);
	}
	
	
	/**
	 * @param xValue the x value of the grid
	 * @param yValue the y value of the grid
	 * @param grid the grid to query over
	 * @return true if the grid value is empty for the current position
	 */
	public static boolean isEmpty(int index, List<Integer> grid){
		return grid.get(index) == null || grid.get(index) == 0;
	}
	
	
	/**
	 * @param grid
	 * @return true if grid is full (i.e. it doesnt contain any zeros) then return true. If it contains any zeros in it then the sudoku will be solved.
	 * 
	 */
	@Deprecated
	//TODO this is just for the UI, can be sorted out later
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
	public static int findLowestValue(int index, 
									  int proposedValue, 
									  List<Integer> knownSafeGrid, 
									  int startNumber, 
									  SolverType solverType, 
									  Map<Integer, Integer> mapOfBoxPositions) {
		// Work out which is the lowest number that we can out in here.
		for (int x=startNumber; x<=9; x++){
			// If the number is valid then set this as the proposed value
			if (ValidValueService.isValid(x, index, knownSafeGrid, solverType, mapOfBoxPositions)){
				return proposedValue = x;
			}
		}
		return 0;
	}
	
}

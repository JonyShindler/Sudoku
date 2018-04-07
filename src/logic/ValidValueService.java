package logic;

import static logic.GridAccessService.findBoxNumber;
import static logic.GridAccessService.findValuesInCurrentBox;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		
//		return !IntStream.range(1, grid.size()-1)
//				.boxed()
//				.anyMatch(i -> rowNumberAndValueMatch(proposedValue, indexInList, grid, i));
	}

	private static boolean rowNumberAndValueMatch(int proposedValue, int indexInList, List<Integer> grid, Integer i) {
		return findRowNumber(i) == findRowNumber(indexInList) 
				&& grid.get(i) != null 
				&& grid.get(i) == proposedValue;
	}
	
	
	/**
	 * Asserts that a proposed addition to a column is valid
	 * @return true is the proposed value is valid in its column.
	 */
	public static boolean isColumnValid(int proposedValue, int indexInList, List<Integer> grid){
		return !IntStream.range(1, grid.size()-1)
				.boxed()
				.anyMatch(i -> columnNumberAndValueMatch(proposedValue, indexInList, grid, i));
	}


	private static boolean columnNumberAndValueMatch(int proposedValue, int indexInList, List<Integer> grid, Integer i) {
		return findColumnNumber(i) == findColumnNumber(indexInList) 
					   && grid.get(i) != null 
					   && grid.get(i) == proposedValue;
	}
	
	
	/**
	 * @return the row number the index appears in, with the 1 to 9 row numbering.
	 */
	public static int findRowNumber(int indexInList) {
		return (int)Math.ceil((indexInList-1)/9) + 1;
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
		int currentBox = findBoxNumber(indexInList, solverType, mapOfBoxPositions);
		return !findValuesInCurrentBox(currentBox, grid, solverType, mapOfBoxPositions).contains(proposedValue);
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
	 * Returns the lowest valid number to go in a specified grid position
	 * 
	 * @return the lowest valid number to go in a grid position
	 */
	public static int findLowestValue(int index, 
									  List<Integer> knownSafeGrid, 
									  int startNumber, 
									  SolverType solverType, 
									  Map<Integer, Integer> mapOfBoxPositions) {
		// Start at the startNumber and increment until a value is found.
		for (int x=startNumber; x<=9; x++){
			// If the number is valid then set this as the proposed value
			if (isValid(x, index, knownSafeGrid, solverType, mapOfBoxPositions)){
				return x;
			}
		}
		return 0;
	}
	
}

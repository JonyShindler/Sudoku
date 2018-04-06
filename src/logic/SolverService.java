package logic;

import logic.GridAccessServices.GridPosition;

/**
 * 
 * Service to solve a sudoku
 * @author Jony
 *
 */
public class SolverService {

	private static final GridPosition startPosition = new GridPosition(0, 0);
	private static final ValidValueService validValueService = new ValidValueService();
	private static final GridAccessServices gridServices = new GridAccessServices();
	
	/**
	 * Solves the sudoku puzzle given a starter grid
	 * @param knownSafeGrid - the starting grid
	 * @param initialGrid - the grid to iterate over (is the same as the other one
	 * @return the solved grid
	 */
	public static int[][] solveSudoku(int[][] knownSafeGrid, int[][] initialGrid) {
		int i,j=0;
		int proposedValue=0;
	
		//need to use the new grid each time.
		for (i=0; i<=8;i++){
			for (j=0; j<=8;j++){
				
				// if a cell is empty then we can put a number in it
				if(validValueService.isEmpty(j, i, knownSafeGrid)){
					//find the lowest valid value to put in the current box
					proposedValue = validValueService.findLowestValue(i, j, proposedValue, validValueService, knownSafeGrid, 1);
					//add the proposed value to the grid
					knownSafeGrid[i][j] = proposedValue;
				}
					//If there was no valid match then backtrack and change the previous number
					while (proposedValue==0){
						
						GridPosition currentPosition = new GridPosition(i, j);
						GridPosition previousPosition = gridServices.backtrackToPreviousCell(currentPosition);
						i = previousPosition.getiValue();
						j = previousPosition.getjValue();
						int previousPositionValue = knownSafeGrid[i][j];
						
						//if the value in the cell of the current position is the same as the input grid, then we cannot change it
						if (knownSafeGrid[i][j] == initialGrid[i][j]){
							continue;
						}
						
						//this means we have expired all options so set the value to 0 so it can get overwritten
						if (previousPositionValue == 9){
							knownSafeGrid[i][j] = 0;
						}
						
						proposedValue = validValueService.findLowestValue(i, j, proposedValue, validValueService, knownSafeGrid, previousPositionValue+1);
						if (proposedValue == 0) {
							if (previousPosition != startPosition){
								knownSafeGrid[i][j] = 0;
							}
						} else {
							knownSafeGrid[i][j] = proposedValue;
						}
					}
			}
		}
		
		return knownSafeGrid;
	}
	
	
}

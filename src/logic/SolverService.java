package logic;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import logic.GridAccessService.GridPosition;

/**
 * 
 * Service to solve a sudoku
 * @author Jony
 *
 */
public class SolverService {

	private static final GridPosition START_POSITION = new GridPosition(0, 0);
	
	public int[][] solveSudoku(int[][] knownSafeGrid, 
							  int[][] initialGrid, 
							  Map<Integer, Integer> mapOfBoxPositions, 
							  SolverType solverType) {

		SolverTask solverTask = new SolverTask(knownSafeGrid, initialGrid, mapOfBoxPositions, solverType);

		// Try and solve the sudoku within 1 second.
		try {
		return Executors.newSingleThreadExecutor().submit(solverTask).get(1, SECONDS);
		} catch (TimeoutException e) { return null;
		} catch (InterruptedException e) { return null;
		} catch (ExecutionException e) { return null;
		}
	}
	
	class SolverTask implements Callable<int[][]> {
		int[][] knownSafeGrid;
		int[][] initialGrid;
		Map<Integer, Integer> mapOfBoxPositions;
		SolverType solverType;
		
		SolverTask(int[][] knownSafeGrid, int[][] initialGrid, Map<Integer, Integer> mapOfBoxPositions, SolverType solverType){
			this.knownSafeGrid = knownSafeGrid;
			this.initialGrid = initialGrid;
			this.mapOfBoxPositions = mapOfBoxPositions;
			this.solverType = solverType;
		}
		
	    @Override
	    public int[][] call() throws TimeoutException {
			return solveSudokuPrivate(knownSafeGrid, initialGrid, mapOfBoxPositions, solverType);
	    }
	}
	
	/**
	 * Solves the sudoku puzzle given a starter grid
	 * @param knownSafeGrid - the starting grid
	 * @param initialGrid - the grid to iterate over (is the same as the other one
	 * @param mapOfBoxPositions - the map of box positions (for solving)
	 * @return the solved grid
	 */
	public static int[][] solveSudokuPrivate(int[][] knownSafeGrid, int[][] initialGrid, Map<Integer, Integer> mapOfBoxPositions, SolverType solverType) {
		final GridAccessService gridServices = new GridAccessService(mapOfBoxPositions, solverType);
		final ValidValueService validValueService = new ValidValueService(gridServices);
		int i,j=0;
		int proposedValue=1;
	
		// Need to use the new grid each time.
		for (i=0; i<=8;i++){
			for (j=0; j<=8;j++){
				
				// If a cell is empty then we can put a number in it
				if(validValueService.isEmpty(j, i, knownSafeGrid)){
					// Find the lowest valid value to put in the current box
					proposedValue = validValueService.findLowestValue(i, j, proposedValue, validValueService, knownSafeGrid, 1);
					// Add the proposed value to the grid
					knownSafeGrid[i][j] = proposedValue;
				}
					// If there was no valid match then backtrack and change the previous number
					while (proposedValue==0){
						
						GridPosition currentPosition = new GridPosition(i, j);
						GridPosition previousPosition = gridServices.backtrackToPreviousCell(currentPosition);
						i = previousPosition.getiValue();
						j = previousPosition.getjValue();
						int previousPositionValue = knownSafeGrid[i][j];
					
						// If the value in the cell of the current position is the same as the input grid, then we cannot change it
						if (knownSafeGrid[i][j] == initialGrid[i][j]){
							continue;
						}
						
						// This means we have expired all options so set the value to 0 so it can get overwritten
						if (previousPositionValue == 9){
							knownSafeGrid[i][j] = 0;
						}
						
						proposedValue = validValueService.findLowestValue(i, j, proposedValue, validValueService, knownSafeGrid, previousPositionValue+1);
						if (proposedValue == 0) {
							if (previousPosition != START_POSITION){
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

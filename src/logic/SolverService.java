package logic;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * 
 * Service to solve a sudoku
 * @author Jony
 *
 */
public class SolverService {

	public List<Integer> solveSudoku(List<Integer> knownSafeGrid, 
							   List<Integer> initialGrid, 
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
	
	class SolverTask implements Callable<List<Integer>> {
		List<Integer> knownSafeGrid;
		List<Integer> initialGrid;
		Map<Integer, Integer> mapOfBoxPositions;
		SolverType solverType;
		
		SolverTask(List<Integer> knownSafeGrid, List<Integer> initialGrid, Map<Integer, Integer> mapOfBoxPositions, SolverType solverType){
			this.knownSafeGrid = knownSafeGrid;
			this.initialGrid = initialGrid;
			this.mapOfBoxPositions = mapOfBoxPositions;
			this.solverType = solverType;
		}
		
	    @Override
	    public List<Integer> call() throws TimeoutException {
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
	public static List<Integer> solveSudokuPrivate(List<Integer> knownSafeGrid, 
												   List<Integer> initialGrid, 
												   Map<Integer, Integer> mapOfBoxPositions, 
												   SolverType solverType) {
		int proposedValue=1;
	
		for (int indexLoop = 1 ; indexLoop <=81 ; indexLoop++) {
			if(ValidValueService.isEmpty(indexLoop, knownSafeGrid)){
				// Find the lowest valid value to put in the current box
				proposedValue = ValidValueService.findLowestValue(indexLoop, knownSafeGrid, 1, solverType, mapOfBoxPositions);
				// Add the proposed value to the grid
				knownSafeGrid.set(indexLoop, proposedValue);
			}
			
			while (proposedValue==0){
				//This needs to decrease each time. and it doesnt work with the loop so well. cos it will just
				//restart from the same index loop we were on. so we need to decrease it.
				indexLoop--;
				int previousPositionValue = knownSafeGrid.get(indexLoop);
			
				// If the value in the cell of the current position is the same as the input grid, then we cannot change it
				if (knownSafeGrid.get(indexLoop) == initialGrid.get(indexLoop)){
					continue;
				}
				
				// Otherwise we have expired all options so set the value to 0 so it can get overwritten
				if (previousPositionValue == 9){
					knownSafeGrid.set(indexLoop, 0);
				}
				
				// Attempt to increment the previous cell.
				proposedValue = ValidValueService.findLowestValue(indexLoop, knownSafeGrid, previousPositionValue+1, solverType, mapOfBoxPositions);
				if (proposedValue == 0) {
					if (indexLoop-1 != 1){
						knownSafeGrid.set(indexLoop, 0);
					}
				} else {
					knownSafeGrid.set(indexLoop, proposedValue);
				}
			}
			
		}
		
		return knownSafeGrid;
	}
	
	
}

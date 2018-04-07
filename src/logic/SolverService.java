package logic;

import static java.util.concurrent.TimeUnit.SECONDS;
import static logic.ValidValueService.findLowestValue;

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

	public List<Integer> solveSudoku(List<Integer> workingGrid, 
							   List<Integer> starterValuesGrid, 
							   Map<Integer, Integer> boxPositions, 
							   SolverType solverType) {

		SolverTask solverTask = new SolverTask(workingGrid, starterValuesGrid, boxPositions, solverType);

		// Try and solve the sudoku within 1 second.
		try {
		return Executors.newSingleThreadExecutor().submit(solverTask).get(1, SECONDS);
		} catch (TimeoutException e) { return null;
		} catch (InterruptedException e) { return null;
		} catch (ExecutionException e) { return null;
		}
	}
	
	class SolverTask implements Callable<List<Integer>> {
		List<Integer> workingGrid;
		List<Integer> starterValuesGrid;
		Map<Integer, Integer> boxPositions;
		SolverType solverType;
		
		SolverTask(List<Integer> workingGrid, List<Integer> starterValuesGrid, Map<Integer, Integer> mapOfBoxPositions, SolverType solverType){
			this.workingGrid = workingGrid;
			this.starterValuesGrid = starterValuesGrid;
			this.boxPositions = mapOfBoxPositions;
			this.solverType = solverType;
		}
		
	    @Override
	    public List<Integer> call() throws TimeoutException {
			return solveSudokuPrivate(workingGrid, starterValuesGrid, boxPositions, solverType);
	    }
	}
	
	/**
	 * Solves the sudoku puzzle given a starter grid
	 * @param workingGrid - the starting grid of values which will be modified
	 * @param starterValuesGrid - the grid of the initial values passed in by the user for solving that cannot change.
	 * @param boxPositions - the map of box positions (for solving)
	 * @return the solved grid
	 */
	public static List<Integer> solveSudokuPrivate(List<Integer> workingGrid, 
												   List<Integer> starterValuesGrid, 
												   Map<Integer, Integer> boxPositions, 
												   SolverType solverType) {
		int proposedValue=1;
	
		for (int indexLoop = 1 ; indexLoop <=81 ; indexLoop++) {
			if(ValidValueService.isEmpty(indexLoop, workingGrid)){
				// Find the lowest valid value to put in the current box
				proposedValue = ValidValueService.findLowestValue(indexLoop, workingGrid, 1, solverType, boxPositions);
				// Add the proposed value to the grid
				workingGrid.set(indexLoop, proposedValue);
			}
			
			while (proposedValue==0){
				// If we could not assign a real number to a square, then go back to the previous square and modify it.
				indexLoop--;
				int previousPositionValue = workingGrid.get(indexLoop);
			
				// If that cell contains a fixed value, then it cannot be changed.
				if (workingGrid.get(indexLoop) == starterValuesGrid.get(indexLoop)){
					continue;
				}
				
				// If its on 9 then we have expired all options so set the value to 0 so it can get overwritten
				if (previousPositionValue == 9){
					workingGrid.set(indexLoop, 0);
				}
				
				// Attempt to increment the previous cell.
				proposedValue = findLowestValue(indexLoop, workingGrid, previousPositionValue+1, solverType, boxPositions);
				if (proposedValue == 0 && indexLoop-1 != 1) {
					workingGrid.set(indexLoop, 0);
				} else {
					workingGrid.set(indexLoop, proposedValue);
				}
			}
			
		}
		return workingGrid;
	}
	
}

package logic;

import static logic.SolverType.PERCENT;
import static logic.ValidValueService.findColumnNumber;
import static logic.ValidValueService.findRowNumber;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service to access numbers on the grid
 * @author Jony
 *
 */
public class GridAccessService {
	
	/**
	 * @param position the grid position to enquire over
	 * @return the current 3x3 box that the cell is in
	 */
	public static int findBoxNumber(int index, SolverType solverType, Map<Integer, Integer> mapOfBoxPositions){
		return findBox(solverType, mapOfBoxPositions, index);
	}

	
	/**
	 * @param currentBox the 3x3 box to enquire over 
	 * @param grid the grid in its current state
	 * @return a list of numbers that currently exist in this current box
	 */
	public static List<Integer> findValuesInCurrentBox(int currentBox, 
													   List<Integer> grid, 
													   SolverType solverType, 
													   Map<Integer, Integer> boxPositions){
		return IntStream.range(1, 81)
			.boxed()
			.filter(i -> findBox(solverType, boxPositions, i) == currentBox && grid.get(i) != null)
			.map(i -> grid.get(i))
			.collect(Collectors.toList());
	}


	private static int findBox(SolverType solverType, Map<Integer, Integer> boxPositions, int index) {
		int loopedBox;
		if (solverType == PERCENT){
			loopedBox = boxPositions.get(index);
		} else {
			loopedBox = findBoxForStandard3x3BoxSudoku(findRowNumber(index), findColumnNumber(index));
		}
		return loopedBox;
	}
	
	
	/**
	 * @return the box number based on the i and j position given
	 */
	private static int findBoxForStandard3x3BoxSudoku(int i, int j) {
		int box = 0;
		if (i<=3 && j<=3){ box = 1; }
		
		if (i<=3 && j<=6 && j>3){ box = 2; }
		
		if (i<=3 && j>6){ box = 3; }
		
		if (i<=6 && i>3 && j<=3){ box = 4; }
		
		if (i>3 && i<=6 && j>=7){ box = 6; }
		
		if (i>=7 && j<=3){ box = 7; }
		
		if (i>=7 && j>3 && j<=6){ box = 8; }
		
		if (i>=7 && j>=7){ box = 9; }
		
		if (box == 0){ box = 5; }
		
		return box;
	}
	
}

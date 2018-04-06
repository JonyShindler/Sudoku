package exectuer;

import java.util.Map;

import javax.swing.JFrame;

import gui.BoxesWith3ButtonsForMultiEntry;
import gui.PrinterService;
import logic.BuildGridFromInputsService;
import logic.SolverService;
import logic.SolverType;

/**
 *
 * This method has the public run method to create the GUI.
 *
 * @author Jony
 */
public class Executor2 {

	private static final SolverService solverService = new SolverService();
	private static JFrame frame;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setupFrame();
	}

	
	public static void setupFrame() {
		//Create a 9 x 9 GUI of boxes.
		
		  frame = new BoxesWith3ButtonsForMultiEntry();
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		  frame.setSize(400,400);
		  frame.setResizable(false);
	}
	
	/**
	 * Solve the inputted grid(either for hard coded or GUI, print to the console and return the solution grid.
	 * @param mapOfInputValues
	 * @param mapOfBoxPositions
	 * @return
	 */
	public static int[][] executeForPercentGrid(Map<Integer, Integer> mapOfInputValues, Map<Integer, Integer> mapOfBoxPositions, SolverType solverType) {
		int [][] inputtedGrid = new int[9][9];
		int [][] workingGrid = new int[9][9];
		int [][] solutionGrid = new int[9][9];
		
		solutionGrid = solveForGUI(mapOfInputValues, inputtedGrid, workingGrid, mapOfBoxPositions, solverType);
		
		//Output result to the console
		PrinterService.printToConsole(solutionGrid);
		
		//return the solution so that the GUI can populate itself with the results
		return solutionGrid;
	}


	
	private static int[][] solveForGUI(Map<Integer, Integer> mapOfInputValues, int[][] inputtedGrid, 
			int[][] workingGrid, Map<Integer, Integer> mapOfBoxPositions, SolverType solverType) {
		int[][] solutionGrid;
		//solve it properly
		inputtedGrid = BuildGridFromInputsService.inputValuesIntoRowsFromInputter(mapOfInputValues, inputtedGrid);
		workingGrid = BuildGridFromInputsService.inputValuesIntoRowsFromInputter(mapOfInputValues, workingGrid);
		solutionGrid = solverService.solveSudoku(workingGrid, inputtedGrid, mapOfBoxPositions, solverType);
		return solutionGrid;
	}

}

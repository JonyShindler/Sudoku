package exectuer;

import static logic.BuildGridFromInputsService.createListFromInputMap;

import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import gui.BoxesWith3ButtonsForMultiEntry;
import gui.PrinterService;
import logic.SolverService;
import logic.SolverType;

/**
 *
 * This method has the public run method to create the GUI.
 *
 * @author Jony
 */
public class SudokuSolver {

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
	
	
	public static List<Integer> solveSudoku(Map<Integer, Integer> mapOfInputValues, //key = index position, value = value number
													  Map<Integer, Integer> mapOfBoxPositions, //key = index position, value = box number
													  SolverType solverType) {
		
		List<Integer> solutionGrid = solveForGUI(mapOfInputValues, mapOfBoxPositions, solverType);
		
		//Output result to the console
		PrinterService.printToConsole(solutionGrid);
		
		//return the solution so that the GUI can populate itself with the results
		return solutionGrid;
	}


	private static List<Integer> solveForGUI(Map<Integer, Integer> mapOfInputValues, 
											 Map<Integer, Integer> mapOfBoxPositions, 
											 SolverType solverType) {
		// Give this service the input values and get it to give us back something.
		List<Integer> inputtedGrid = createListFromInputMap(mapOfInputValues);
		// Working grid is a replica of the input grid by the UI.
		List<Integer> workingGrid = createListFromInputMap(mapOfInputValues);
		List<Integer> solutionGrid = new SolverService().solveSudoku(workingGrid, inputtedGrid, mapOfBoxPositions, solverType);
		return solutionGrid;
	}

}

package exectuer;

import java.util.HashMap;

import javax.swing.JFrame;

import gui.BoxesWith3ButtonsForMultiEntry;
import gui.BoxesWith9Buttons;
import gui.BoxesWithButtons;
import gui.Outputter;
import gui.PrinterService;
import logic.BuildGridFromInputsService;
import logic.GridAccessService;
import logic.GridAccessService.GridPosition;
import logic.SolverService;
import logic.SolverType;
import logic.ValidValueService;

/**
 *
 * This method has the public run method to create the GUI.
 *
 * @author Jony
 */
public class Executor2 {

	private static final BuildGridFromInputsService buildGridFromInputsService = new BuildGridFromInputsService();
	private static final SolverService solverService = new SolverService();
	private static JFrame frame;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
	public static int[][] executeForPercentGrid(HashMap<Integer, Integer> mapOfInputValues, HashMap<Integer, Integer> mapOfBoxPositions, SolverType solverType) {
		int [][] inputtedGrid = new int[9][9];
		int [][] workingGrid = new int[9][9];
		int [][] solutionGrid = new int[9][9];
		
		solutionGrid = solveForGUI(mapOfInputValues, inputtedGrid, workingGrid, mapOfBoxPositions, solverType);
//		solutionGrid = solveForHardCoded(solverType);
		
		//Output result to the console
		PrinterService.printToConsole(solutionGrid);
		
		//return the solution so that the GUI can populate itself with the results
		return solutionGrid;
	}

	//This was only used for local testing. Unit tests would be better.
	private static int[][] solveForHardCoded(SolverType solverType) {
		HashMap<Integer, Integer> mapOfBoxPositions = new HashMap<>();
		int [][] inputtedGrid = new int[9][9];
		int [][] practiceGrid = new int[9][9];
		int [][] solutionGrid;
		//solve it for hard coded
		  BuildGridFromInputsService.inputValuesIntoRowsForPercentExample();
		  practiceGrid = BuildGridFromInputsService.fillGridWithValuesFromRows(practiceGrid);
		  inputtedGrid = BuildGridFromInputsService.fillGridWithValuesFromRows(inputtedGrid);
		  solutionGrid = solverService.solveSudoku(practiceGrid, inputtedGrid, mapOfBoxPositions, solverType);
		return solutionGrid;
	}

	
	private static int[][] solveForGUI(HashMap<Integer, Integer> map, int[][] inputtedGrid, 
			int[][] workingGrid, HashMap<Integer, Integer> mapOfBoxPositions, SolverType solverType) {
		int[][] solutionGrid;
		//solve it properly
		inputtedGrid = BuildGridFromInputsService.inputValuesIntoRowsFromInputter(map, inputtedGrid);
		workingGrid = BuildGridFromInputsService.inputValuesIntoRowsFromInputter(map, workingGrid);
		solutionGrid = solverService.solveSudoku(workingGrid, inputtedGrid, mapOfBoxPositions, solverType);
		return solutionGrid;
	}

}

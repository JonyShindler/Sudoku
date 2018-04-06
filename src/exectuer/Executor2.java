package exectuer;

import java.util.HashMap;

import javax.swing.JFrame;

import gui.BoxesWithButtons;
import gui.Outputter;
import gui.PrinterService;
import logic.BuildGridFromInputsService;
import logic.GridAccessServices;
import logic.GridAccessServices.GridPosition;
import logic.SolverService;
import logic.ValidValueService;

/**
 *
 * This method has the public run method to create the GUI.
 *
 * @author Jony
 */
public class Executor2 {

	private static final BuildGridFromInputsService buildGridFromInputsService = new BuildGridFromInputsService();
	private static JFrame frame;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Create a 9 x 9 GUI of boxes.
		  frame = new BoxesWithButtons();
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
		  frame.setSize(220,320);
		  frame.setResizable(false);
		  
//		  int [][] solutionGrid = new int[9][9];
//		  int frameXLocation = frame.getX() + frame.getWidth();
//		  int frameYLocation = frame.getY();
		  
	}

	/**
	 * @param map A map of box numbers to the values in those box numbers
	 * @return the solution to the puzzle.
	 */
	public static int[][] execute(HashMap<Integer, Integer> map) {
		int [][] inputtedGrid = new int[9][9];
		int [][] workingGrid = new int[9][9];
		int [][] solutionGrid = new int[9][9];

		//Inputted grid is the starter grid which never changes
		//Working grid is the grid that is iterated over to find the final result
		//at this point both grids are the same but we need to have to identical independent grids but with different pointers..
		
		solutionGrid = solveForGUI(map, inputtedGrid, workingGrid);
		
//		solutionGrid = solveForHardCoded(inputtedGrid);
		
		//TODO if it is already complete then do nothing.
		
		//Output result to the console
		PrinterService.printToConsole(solutionGrid);
		
		//return the solution so that the GUI can populate itself with the results
		return solutionGrid;
	}

	private static int[][] solveForHardCoded(int[][] inputtedGrid) {
		int[][] solutionGrid;
		//solve it for hard coded
		  int [][] practiceGrid = new int[9][9];
		  BuildGridFromInputsService.inputValuesIntoRowsForSimpleCase();
		  practiceGrid = BuildGridFromInputsService.fillGridWithValuesFromRows(practiceGrid);
		  solutionGrid = SolverService.solveSudoku(practiceGrid, inputtedGrid);
		return solutionGrid;
	}

	private static int[][] solveForGUI(HashMap<Integer, Integer> map, int[][] inputtedGrid, int[][] workingGrid) {
		int[][] solutionGrid;
		//solve it properly
		inputtedGrid = BuildGridFromInputsService.inputValuesIntoRowsFromInputter(map, inputtedGrid);
		workingGrid = BuildGridFromInputsService.inputValuesIntoRowsFromInputter(map, workingGrid);
		solutionGrid = SolverService.solveSudoku(workingGrid, inputtedGrid);
		return solutionGrid;
	}

}

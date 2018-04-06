package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Service to access numbers on the grid
 * @author Jony
 *
 */
public class GridAccessService {
	
	private HashMap<Integer, Integer> mapOfBoxPositions = new HashMap<>();
	private SolverType solverType;
	public GridAccessService(HashMap<Integer, Integer> mapOfBoxPositions, SolverType solverType){
	this.mapOfBoxPositions = mapOfBoxPositions;	
	this.solverType = solverType;
	}
	

	/**
	 * @param positionNumber
	 * @return the grid position (i and j) based on the position number (1 to 81)
	 */
	public static GridPosition calculateGridPositionOfGridNumber(int positionNumber){
		double y = Math.ceil((double)positionNumber/9)-1;
		
		double x = (double)positionNumber % 9 - 1;
		
		if (x==-1){
			x=8;
		}
		// x and y can only be between 1 and 9
		//so if we divide position number by 9 and round down we can get the row numbeR?
		
		GridPosition position = new GridPosition((int)x, (int)y);
		
		return position;
	}
	
	
	/**
	 * @param position the grid position to add a number to
	 * @param numberToAdd the value of the number to add to the grid positon (1 to 9)
	 * @param grid the iterated grid we are working on
	 * @return the updated grid
	 */
	public static int[][] addNumberToGrid(GridPosition position, int numberToAdd, int[][] grid){
		int x = position.getjValue();
		int y = position.getiValue();		
		
		grid[x][y]= numberToAdd;
		return grid;
	}
	
	
	/**
	 * @param currentPosition the grid position of the current cell
	 * @return the grid position of the previous cell
	 */
	public GridPosition backtrackToPreviousCell(GridPosition currentPosition) {
		GridPosition previousPosition = new GridPosition(0, 0);
		
		int iValue = currentPosition.getiValue();
		int jValue = currentPosition.getjValue()-1;
		if (jValue == -1){
			jValue = 8;
			iValue --;
		}
		
		if (iValue == -1){
			iValue = 0;
			jValue = 0;
		}
		
		previousPosition.setiValue(iValue);
		previousPosition.setjValue(jValue);
		
		return previousPosition;
	}
	
	
	/**
	 * Bean class storing i and j position in a grid.
	 * 
	 * @author Jony
	 *
	 */
	public static class GridPosition{
		int iValue;
		int jValue;
		
		public GridPosition(int iValue, int jValue){
			this.iValue = iValue;
			this.jValue = jValue;
		}
		
		public int getiValue() {
			return iValue;
		}
		public void setiValue(int iValue) {
			this.iValue = iValue;
		}
		public int getjValue() {
			return jValue;
		}
		public void setjValue(int jValue) {
			this.jValue = jValue;
		}
		
		@Override 
		 public boolean equals(Object o) { 
		    if (o == this) return true;
	        if (!(o instanceof GridPosition)) {
	            return false;
	        }
	        GridPosition position = (GridPosition) o;
	        return iValue == position.iValue &&
	        		Objects.equals(iValue, position.iValue) &&
	        		Objects.equals(jValue, position.jValue);
	        
			
		}
	}
	
	
	/**
	 * @param position the grid position to enquire over
	 * @return the current 3x3 box that the cell is in
	 */
	public int findBoxCurrentPositionIsIn(GridPosition position){
		
		int i = position.getiValue();
		int j = position.getjValue();
		
		if (solverType == SolverType.PERCENT){
			return findBoxForPercentSudoku(i, j);
		}
		return findBoxForStandard3x3BoxSudoku(i, j);
	}

	
	/**
	 * @param currentBox the 3x3 box to enquire over 
	 * @param grid the grid in its current state
	 * @return a list of numbers that currently exist in this current box
	 */
	public List<Integer> findValuesInCurrentBox(int currentBox, int[][] grid){
		
		//loop over all values in tables
		int i,j=0;
		List<Integer> listInBox= new ArrayList<Integer>();
		int loopedBox;
		
		for (i=0; i<=8;i++){
			for (j=0; j<=8;j++){

				//evaluate the box
				if (solverType == SolverType.PERCENT){
					loopedBox = findBoxForPercentSudoku(i, j);
				} else {
					loopedBox = findBoxForStandard3x3BoxSudoku(i, j);
				}
				
				//if they form part of the box, store the value in a list
				if (loopedBox==currentBox){
					int value = grid[i][j];
					if (value !=0 ) {
						listInBox.add(value);
					}
				}
				
			}
		}

		return listInBox;
	}
	
	
	private int findBoxForPercentSudoku(int i, int j){
		int gridNumber = (9*i)+j+1;
		return mapOfBoxPositions.get(gridNumber);
	}

	
	
	
	private int findBoxForPercentSudokuHardCoded(int i, int j){
		int gridNumber = (9*i)+j;
		
		switch (gridNumber+1){
		//1
		
		case 1  :	return 1;
		case 2  :	return 1; 
		case 3  :   return 2; 
		case 4  :   return 2; 
		case 5  :   return 2; 
		case 6  :   return 2; 
		case 7  :   return 2; 
		case 8  :   return 3; 
		case 9  :   return 3;
		//2
		case 10 :   return 1; 
		case 11 :   return 1; 
		case 12 :   return 1; 
		case 13 :   return 2; 
		case 14 :   return 2; 
		case 15 :   return 2; 
		case 16 :   return 2; 
		case 17 :   return 3; 
		case 18 :   return 3; 
		//3
		case 19 :   return 4; 
		case 20 :   return 1; 
		case 21 :   return 5; 
		case 22 :   return 5; 
		case 23 :   return 5; 
		case 24 :   return 6; 
		case 25 :   return 3; 
		case 26 :   return 3; 
		case 27 :   return 3; 
		//4
		case 28 :   return 4; 
		case 29 :   return 1; 
		case 30 :   return 1; 
		case 31 :   return 1; 
		case 32 :   return 5; 
		case 33 :   return 6; 
		case 34 :   return 6; 
		case 35 :   return 3; 
		case 36 :   return 3; 
		//5
		case 37 :   return 4; 
		case 38 :   return 4; 
		case 39 :   return 4; 
		case 40 :   return 4; 
		case 41 :   return 5; 
		case 42 :   return 6; 
		case 43 :   return 6; 
		case 44 :   return 6; 
		case 45 :   return 6; 
		//6
		case 46 :   return 7; 
		case 47 :   return 7; 
		case 48 :   return 4; 
		case 49 :   return 4; 
		case 50 :   return 5; 
		case 51 :   return 9; 
		case 52 :   return 9; 
		case 53 :   return 9; 
		case 54 :   return 6; 
		//7
		case 55 :   return 7; 
		case 56 :   return 7; 
		case 57 :   return 7; 
		case 58 :   return 4; 
		case 59 :   return 5; 
		case 60 :   return 5; 
		case 61 :   return 5; 
		case 62 :   return 9; 
		case 63 :   return 6; 
		//8
		case 64 :   return 7; 
		case 65 :   return 7; 
		case 66 :   return 8; 
		case 67 :   return 8; 
		case 68 :   return 8; 
		case 69 :   return 8; 
		case 70 :   return 9; 
		case 71 :   return 9; 
		case 72 :   return 9; 
		//9
		case 73 :   return 7; 
		case 74 :   return 7; 
		case 75 :   return 8; 
		case 76 :   return 8; 
		case 77 :   return 8; 
		case 78 :   return 8; 
		case 79 :   return 8; 
		case 80 :   return 9; 
		case 81 :   return 9; 

		
		
			
		}
		
		return mapOfBoxPositions.get(gridNumber);
	}
	
	
	
	
	/**
	 * @param i the i position
	 * @param j the j position
	 * @return the box number based on the i and j position given
	 */
	private int findBoxForStandard3x3BoxSudoku(int i, int j) {
		int box = 0;
		if (i<=2 && j<=2){
			box = 1;
		}
		
		if (i<=2 && j<=5 && j>2){
			box = 2;
		}
		
		if (i<=2 && j>5){
			box = 3;
		}
		
		if (i<=5 && i>2 && j<=2){
			box = 4;
		}
		
		if (i>2 && i<=5 && j>=6){
			box = 6;
		}
		
		if (i>=6 && j<=2){
			box = 7;
		}
		
		if (i>=6 && j>2 && j<=5){
			box = 8;
		}
		
		if (i>=6 && j>=6){
			box = 9;
		}
		
		if (box == 0){
			box = 5;
		}
		
		return box;
	}
	
}

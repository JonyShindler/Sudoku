package logic;

import static logic.CreateBlankGrid.createRowArray;

import java.util.HashMap;
import java.util.Map;

import logic.GridAccessServices.GridPosition;

/**
 *
 * Service to populate the initial starting 9x9 grid with input values.
 * @author Jony
 */
public class BuildGridFromInputsService {

	private static Map<Integer, Integer> row0Map;
	private static Map<Integer, Integer> row1Map;
	private static HashMap<Integer, Integer> row2Map;
	private static Map<Integer, Integer> row3Map;
	private static HashMap<Integer, Integer> row4Map;
	private static HashMap<Integer, Integer> row5Map;
	private static HashMap<Integer, Integer> row6Map;
	private static HashMap<Integer, Integer> row7Map;
	private static HashMap<Integer, Integer> row8Map;
	
	
	/**
	 * @param grid the 9x9 grid populated with all input values
	 * @return the 9x9 grid with the values in it
	 */
	public static int[][] fillGridWithValuesFromRows(int[][] grid) {
		int[] row0 = createRowArray(row0Map);
		int[] row1 = createRowArray(row1Map);
		int[] row2 = createRowArray(row2Map);
		int[] row3 = createRowArray(row3Map);
		int[] row4 = createRowArray(row4Map);
		int[] row5 = createRowArray(row5Map);
		int[] row6 = createRowArray(row6Map);
		int[] row7 = createRowArray(row7Map);
		int[] row8 = createRowArray(row8Map);
		
		grid[0]=row0;
		grid[1]=row1;
		grid[2]=row2;
		grid[3]=row3;
		grid[4]=row4;
		grid[5]=row5;
		grid[6]=row6;
		grid[7]=row7;
		grid[8]=row8;
		
		return grid;
	}
	
	
	/**Populates a 9x9 grid from the values inputted via the GUI
	 * @param map a map of box number to value in that box
	 * @param grid the 9 by 9 grid to populate
	 * @return the grid of input values via the GUI
	 */
	public static int[][] inputValuesIntoRowsFromInputter(HashMap<Integer, Integer> map, int[][] grid){
		//for each element in the map from the input GUI we want to find out which row/position in the 9x9 grid it goes in
		
		for (Map.Entry<Integer, Integer> entry : map.entrySet()){
			//calculate the grid position of each of the keys.
			GridPosition position = GridAccessServices.calculateGridPositionOfGridNumber(entry.getKey());
			//add the value to the just found grid position;
			grid = GridAccessServices.addNumberToGrid(position, entry.getValue(), grid);
			
		}
		return grid;
		
	}

	
	/**
	 * Hard codes input values into the grid.
	 */
	public static void inputValuesIntoRowsForSimpleCase(){
		row0Map = new HashMap<Integer, Integer>();
		row0Map.put(2, 8);
		
		row1Map = new HashMap<Integer, Integer>();
		row1Map.put(4, 9);
		row1Map.put(5, 2);
		row1Map.put(7, 7);
		row1Map.put(8, 4);
		
		row2Map = new HashMap<Integer, Integer>();
		row2Map.put(1, 9);
		row2Map.put(4, 1);
		row2Map.put(5, 7);
		row2Map.put(6, 5);
		row2Map.put(7, 2);
		
		row3Map = new HashMap<Integer, Integer>();
		row3Map.put(0, 3);
		row3Map.put(1, 7);
		row3Map.put(3, 1);
		row3Map.put(4, 5);
		row3Map.put(5, 9);
		row3Map.put(6, 4);
		
		row4Map = new HashMap<Integer, Integer>();
		row4Map.put(0, 9);
		row4Map.put(8, 3);
	
		row5Map = new HashMap<Integer, Integer>();
		row5Map.put(2, 6);
		row5Map.put(3, 7);
		row5Map.put(4, 4);
		row5Map.put(5, 3);
		row5Map.put(7, 1);
		row5Map.put(8, 9);
		
		row6Map = new HashMap<Integer, Integer>();
		row6Map.put(1, 4);
		row6Map.put(2, 5);
		row6Map.put(3, 9);
		row6Map.put(4, 7);
		row6Map.put(7, 3);
		
		row7Map = new HashMap<Integer, Integer>();
		row7Map.put(0, 6);
		row7Map.put(1, 8);
		row7Map.put(3, 3);
		row7Map.put(4, 2);
		
		row8Map = new HashMap<Integer, Integer>();
		row8Map.put(6, 9);
	}
	
	
}

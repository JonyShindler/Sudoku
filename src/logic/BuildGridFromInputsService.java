package logic;

import java.util.Map;

import logic.GridAccessService.GridPosition;

/**
 *
 * Service to populate the initial starting 9x9 grid with input values.
 * @author Jony
 */
public class BuildGridFromInputsService {

	/**Populates a 9x9 grid from the values inputted via the GUI
	 * @param map a map of box number to value in that box
	 * @param grid the 9 by 9 grid to populate
	 * @return the grid of input values via the GUI
	 */
	public static int[][] inputValuesIntoRowsFromInputter(Map<Integer, Integer> map, int[][] grid){
		//for each element in the map from the input GUI we want to find out which row/position in the 9x9 grid it goes in
		
		for (Map.Entry<Integer, Integer> entry : map.entrySet()){
			//calculate the grid position of each of the keys.
			GridPosition position = GridAccessService.calculateGridPositionOfGridNumber(entry.getKey());
			//add the value to the just found grid position;
			grid = GridAccessService.addNumberToGrid(position, entry.getValue(), grid);
			
		}
		return grid;
		
	}	
	
}

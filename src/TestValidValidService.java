import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import logic.SolverType;
import logic.ValidValueService;

public class TestValidValidService {
	
	
	@Test
	public void testIsRowValidForEndOfRow2(){
		// Given
		Integer[] expectedRow1 = {7,9,6,8,5,4,3,2,1};
		Integer[] expectedRow2 = {2,4,3,1,7,6,9,8};
		//Try and put a 5 in last position of row 2.
		
		List<Integer> row1 = Arrays.asList(expectedRow1);
		List<Integer> row2 = Arrays.asList(expectedRow2);
		
		List<Integer> currentGrid = new ArrayList<Integer>();
		currentGrid.add(null);
		currentGrid.addAll(row1);
		currentGrid.addAll(row2);
		
		// When & Then
		assertTrue(ValidValueService.isRowValid(5, 18, currentGrid));
	}
	
	
	@Test
	public void testIsRowValidForPenultimateOn4(){
		// Given
		Integer[] expectedRow1 = {7,9,6,8,5,4,3,2,1};
		Integer[] expectedRow2 = {2,4,3,1,7,6,9,8,5};
		Integer[] expectedRow3 = {8,5,1,2,3,9,4,7,6};
		//Try and put a 1 in the penultimate space of row 4. // should be 4 then a 2.
		Integer[] expectedRow4 = {1,3,7,9,6,5,8, null, null};
		
		List<Integer> row1 = Arrays.asList(expectedRow1);
		List<Integer> row2 = Arrays.asList(expectedRow2);
		List<Integer> row3 = Arrays.asList(expectedRow3);
		List<Integer> row4 = Arrays.asList(expectedRow4);
		
		//Remember to put a blank in position 0.
		
		List<Integer> currentGrid = new ArrayList<Integer>();
		currentGrid.add(null);
		currentGrid.addAll(row1);
		currentGrid.addAll(row2);
		currentGrid.addAll(row3);
		currentGrid.addAll(row4);
		
		// When & Then
		assertFalse(ValidValueService.isRowValid(1, 35, currentGrid));
	}
	
	
	@Test
	public void testIsColumnValid(){
		// Given
		Integer[] expectedRow1 = {7,9,6,8,5,4,3,2,1};
		Integer[] expectedRow2 = {2,4,3,1,7,6,9,8,5};
		
		List<Integer> row1 = Arrays.asList(expectedRow1);
		List<Integer> row2 = Arrays.asList(expectedRow2);
		
		List<Integer> currentGrid = new ArrayList<Integer>();
		currentGrid.add(null);
		currentGrid.addAll(row1);
		currentGrid.addAll(row2);
		currentGrid.add(null);

		// When & Then
		assertTrue(ValidValueService.isColumnValid(1, 19, currentGrid));
		assertFalse(ValidValueService.isColumnValid(2, 19, currentGrid));
	}
	

	@Test
	public void testIsBoxValid() {
		Integer[] expectedRow1 = {7,9,6,8,5,4,3,2,1};
		Integer[] expectedRow2 = {2,4,3,1,7,6,9,8,5};
		Integer[] expectedRow3 = {8,5,null,null,3,9,4,7,6};
		//Try and put a 1 in the third column of row 3 and a 2 in 22.
		
		List<Integer> row1 = Arrays.asList(expectedRow1);
		List<Integer> row2 = Arrays.asList(expectedRow2);
		List<Integer> row3 = Arrays.asList(expectedRow3);
		
		List<Integer> currentGrid = new ArrayList<Integer>();
		currentGrid.add(null);
		currentGrid.addAll(row1);
		currentGrid.addAll(row2);
		currentGrid.addAll(row3);
		
		// When & Then
		//For first box
		assertTrue(ValidValueService.isBoxValid(1, 21, currentGrid, SolverType.NORMAL, null));
		assertFalse(ValidValueService.isBoxValid(2, 21, currentGrid, SolverType.NORMAL, null));

		//For second box
		assertTrue(ValidValueService.isBoxValid(2, 22, currentGrid, SolverType.NORMAL, null));
		assertFalse(ValidValueService.isBoxValid(1, 22, currentGrid, SolverType.NORMAL, null));
	}
	
}

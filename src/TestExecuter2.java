import static logic.SolverType.NORMAL;
import static logic.SolverType.PERCENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import exectuer.SudokuSolver;
import logic.SolverType;

public class TestExecuter2 {
	
	@Test
	public void testTheBasicSudokuWithLists() {
		// Given
		SolverType solverType = NORMAL;
		Map<Integer, Integer> inputs = new HashMap<Integer, Integer>();
		
		inputs.put(1, 7); inputs.put(2, 9); inputs.put(7, 3);
		inputs.put(15, 6); inputs.put(16, 9);
		inputs.put(19, 8); inputs.put(23, 3); inputs.put(26, 7); inputs.put(27, 6);
		inputs.put(33, 5); inputs.put(36, 2);
		inputs.put(39, 5); inputs.put(40, 4); inputs.put(41, 1); inputs.put(42, 8); inputs.put(43, 7);
		inputs.put(46, 4); inputs.put(49, 7);
		inputs.put(55, 6); inputs.put(56, 1); inputs.put(59, 9); inputs.put(63, 8);
		inputs.put(66, 2); inputs.put(67, 3);
		inputs.put(75, 9); inputs.put(80, 5); inputs.put(81, 4);
		
		// When
		List<Integer> solutionGrid = SudokuSolver.solveSudoku(inputs, null, solverType);
		
		List<Integer> expectedResult = new ArrayList<>();
		
		Integer[] expectedRow1 = {7,9,6,8,5,4,3,2,1};
		Integer[] expectedRow2 = {2,4,3,1,7,6,9,8,5};
		Integer[] expectedRow3 = {8,5,1,2,3,9,4,7,6};
		Integer[] expectedRow4 = {1,3,7,9,6,5,8,4,2};
		Integer[] expectedRow5 = {9,2,5,4,1,8,7,6,3};
		Integer[] expectedRow6 = {4,6,8,7,2,3,5,1,9};
		Integer[] expectedRow7 = {6,1,4,5,9,7,2,3,8};
		Integer[] expectedRow8 = {5,8,2,3,4,1,6,9,7};
		Integer[] expectedRow9 = {3,7,9,6,8,2,1,5,4};
		
		List<Integer> row1 = Arrays.asList(expectedRow1); List<Integer> row2 = Arrays.asList(expectedRow2);
		List<Integer> row3 = Arrays.asList(expectedRow3); List<Integer> row4 = Arrays.asList(expectedRow4);
		List<Integer> row5 = Arrays.asList(expectedRow5); List<Integer> row6 = Arrays.asList(expectedRow6);
		List<Integer> row7 = Arrays.asList(expectedRow7); List<Integer> row8 = Arrays.asList(expectedRow8);
		List<Integer> row9 = Arrays.asList(expectedRow9);
		
		expectedResult.add(null); expectedResult.addAll(row1); expectedResult.addAll(row2); expectedResult.addAll(row3); 
		expectedResult.addAll(row4); expectedResult.addAll(row5); expectedResult.addAll(row6); expectedResult.addAll(row7);
		expectedResult.addAll(row8); expectedResult.addAll(row9);
		
		// Then
		Assert.assertEquals(expectedResult, solutionGrid);
	}
	
	
	@Test
	public void testSquigglySudokuWithLists() {
		// Given
		SolverType solverType = PERCENT;
		Map<Integer, Integer> inputs = new HashMap<Integer, Integer>();
		Map<Integer, Integer> boxes = new HashMap<Integer, Integer>();
		
		inputs.put(1, 1); inputs.put(2, 6); inputs.put(5, 7); inputs.put(6, 9); inputs.put(8, 8); inputs.put(9, 4);
		inputs.put(10, 9); inputs.put(11, 3); inputs.put(15, 6); inputs.put(17, 1);
		// blank
		inputs.put(28, 8); inputs.put(30, 1); inputs.put(34, 6); inputs.put(36, 5); 
		inputs.put(38, 7); inputs.put(39, 3); inputs.put(40, 1); inputs.put(41, 5); inputs.put(42, 8); inputs.put(43, 4); inputs.put(44, 9);
		inputs.put(46, 5); inputs.put(48, 6); inputs.put(51, 1); inputs.put(54, 9);
		inputs.put(61, 1);
		inputs.put(67, 5); inputs.put(71, 4); inputs.put(72, 1);
		inputs.put(73, 3); inputs.put(74, 1); inputs.put(76, 9); inputs.put(77, 4); inputs.put(80, 6); inputs.put(81, 8);
		
		boxes.put(1,1);boxes.put(2,1);boxes.put(3,1);boxes.put(4,1);boxes.put(5,2);boxes.put(6,3);boxes.put(7,3);boxes.put(8,3);boxes.put(9,3);
		boxes.put(10,1);boxes.put(11,1);boxes.put(12,1);boxes.put(13,2);boxes.put(14,2);boxes.put(15,2);boxes.put(16,2);boxes.put(17,3);boxes.put(18,3);
		boxes.put(19,1);boxes.put(20,4);boxes.put(21,4);boxes.put(22,2);boxes.put(23,5);boxes.put(24,5);boxes.put(25,2);boxes.put(26,6);boxes.put(27,3);
		boxes.put(28,1);boxes.put(29,4);boxes.put(30,2);boxes.put(31,2);boxes.put(32,5);boxes.put(33,5);boxes.put(34,6);boxes.put(35,6);boxes.put(36,3);
		boxes.put(37,7);boxes.put(38,4);boxes.put(39,4);boxes.put(40,4);boxes.put(41,5);boxes.put(42,6);boxes.put(43,6);boxes.put(44,6);boxes.put(45,3);
		boxes.put(46,7);boxes.put(47,4);boxes.put(48,4);boxes.put(49,5);boxes.put(50,5);boxes.put(51,8);boxes.put(52,8);boxes.put(53,6);boxes.put(54,9);
		boxes.put(55,7);boxes.put(56,4);boxes.put(57,8);boxes.put(58,5);boxes.put(59,5);boxes.put(60,8);boxes.put(61,6);boxes.put(62,6);boxes.put(63,9);
		boxes.put(64,7);boxes.put(65,7);boxes.put(66,8);boxes.put(67,8);boxes.put(68,8);boxes.put(69,8);boxes.put(70,9);boxes.put(71,9);boxes.put(72,9);
		boxes.put(73,7);boxes.put(74,7);boxes.put(75,7);boxes.put(76,7);boxes.put(77,8);boxes.put(78,9);boxes.put(79,9);boxes.put(80,9);boxes.put(81,9);
		
		// When
		List<Integer> solutionGrid = SudokuSolver.solveSudoku(inputs, boxes, solverType);
		List<Integer> expectedResult = new ArrayList<>();
		
		// Then
		Integer[] expectedRow1 = {1,6,5,2,7,9,3,8,4};
		Integer[] expectedRow2 = {9,3,4,8,2,6,5,1,7};
		Integer[] expectedRow3 = {7,5,8,4,1,2,9,3,6};
		Integer[] expectedRow4 = {8,2,1,3,9,4,6,7,5};
		Integer[] expectedRow5 = {6,7,3,1,5,8,4,9,2};
		Integer[] expectedRow6 = {5,4,6,7,3,1,8,2,9};
		Integer[] expectedRow7 = {4,9,2,6,8,7,1,5,3};
		Integer[] expectedRow8 = {2,8,9,5,6,3,7,4,1};
		Integer[] expectedRow9 = {3,1,7,9,4,5,2,6,8};
		
		List<Integer> row1 = Arrays.asList(expectedRow1); List<Integer> row2 = Arrays.asList(expectedRow2);
		List<Integer> row3 = Arrays.asList(expectedRow3); List<Integer> row4 = Arrays.asList(expectedRow4);
		List<Integer> row5 = Arrays.asList(expectedRow5); List<Integer> row6 = Arrays.asList(expectedRow6);
		List<Integer> row7 = Arrays.asList(expectedRow7); List<Integer> row8 = Arrays.asList(expectedRow8);
		List<Integer> row9 = Arrays.asList(expectedRow9);
		
		expectedResult.add(null); expectedResult.addAll(row1); expectedResult.addAll(row2); expectedResult.addAll(row3);
		expectedResult.addAll(row4); expectedResult.addAll(row5); expectedResult.addAll(row6); expectedResult.addAll(row7);
		expectedResult.addAll(row8); expectedResult.addAll(row9);
		
		// Then
		Assert.assertEquals(expectedResult, solutionGrid);
	}
}

import static logic.SolverType.NORMAL;
import static logic.SolverType.PERCENT;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import exectuer.Executor2;
import logic.SolverType;

public class TestExecuter2 {

	@Test
	public void testTheBasicSudoku() {
		// Given
		SolverType solverType = NORMAL;
		Map<Integer, Integer> mapOfInputValues = new HashMap<Integer, Integer>();
		
		mapOfInputValues.put(1, 7);
		mapOfInputValues.put(2, 9);
		mapOfInputValues.put(7, 3);
		
		mapOfInputValues.put(15, 6);
		mapOfInputValues.put(16, 9);
		
		mapOfInputValues.put(19, 8);
		mapOfInputValues.put(23, 3);
		mapOfInputValues.put(26, 7);
		mapOfInputValues.put(27, 6);
		
		mapOfInputValues.put(33, 5);
		mapOfInputValues.put(36, 2);
		
		mapOfInputValues.put(39, 5);
		mapOfInputValues.put(40, 4);
		mapOfInputValues.put(41, 1);
		mapOfInputValues.put(42, 8);
		mapOfInputValues.put(43, 7);
		
		mapOfInputValues.put(46, 4);
		mapOfInputValues.put(49, 7);
		
		mapOfInputValues.put(55, 6);
		mapOfInputValues.put(56, 1);
		mapOfInputValues.put(59, 9);
		mapOfInputValues.put(63, 8);
		
		mapOfInputValues.put(66, 2);
		mapOfInputValues.put(67, 3);
		
		mapOfInputValues.put(75, 9);
		mapOfInputValues.put(80, 5);
		mapOfInputValues.put(81, 4);
		
		// When
		int[][] solutionGrid = Executor2.executeForPercentGrid(mapOfInputValues, null, solverType);
		
		// Then
		int[] expectedRow1 = {7,9,6,8,5,4,3,2,1};
		int[] expectedRow2 = {2,4,3,1,7,6,9,8,5};
		int[] expectedRow3 = {8,5,1,2,3,9,4,7,6};
		
		int[] expectedRow4 = {1,3,7,9,6,5,8,4,2};
		int[] expectedRow5 = {9,2,5,4,1,8,7,6,3};
		int[] expectedRow6 = {4,6,8,7,2,3,5,1,9};
		
		int[] expectedRow7 = {6,1,4,5,9,7,2,3,8};
		int[] expectedRow8 = {5,8,2,3,4,1,6,9,7};
		int[] expectedRow9 = {3,7,9,6,8,2,1,5,4};
		Assert.assertArrayEquals(expectedRow1, solutionGrid[0]);
		Assert.assertArrayEquals(expectedRow2, solutionGrid[1]);
		Assert.assertArrayEquals(expectedRow3, solutionGrid[2]);
		
		Assert.assertArrayEquals(expectedRow4, solutionGrid[3]);
		Assert.assertArrayEquals(expectedRow5, solutionGrid[4]);
		Assert.assertArrayEquals(expectedRow6, solutionGrid[5]);
		
		Assert.assertArrayEquals(expectedRow7, solutionGrid[6]);
		Assert.assertArrayEquals(expectedRow8, solutionGrid[7]);
		Assert.assertArrayEquals(expectedRow9, solutionGrid[8]);
		
	}
	
	
	@Test
	public void testSquigglySudoku() {
		// Given
		SolverType solverType = PERCENT;
		Map<Integer, Integer> mapOfInputValues = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapOfBoxPositions = new HashMap<Integer, Integer>();
		
		mapOfInputValues.put(1, 1);
		mapOfInputValues.put(2, 6);
		mapOfInputValues.put(5, 7);
		mapOfInputValues.put(6, 9);
		mapOfInputValues.put(8, 8);
		mapOfInputValues.put(9, 4);
		
		mapOfInputValues.put(10, 9);
		mapOfInputValues.put(11, 3);
		mapOfInputValues.put(15, 6);
		mapOfInputValues.put(17, 1);
		
		//blank
		
		mapOfInputValues.put(28, 8);
		mapOfInputValues.put(30, 1);
		mapOfInputValues.put(34, 6);
		mapOfInputValues.put(36, 5);
		
		mapOfInputValues.put(38, 7);
		mapOfInputValues.put(39, 3);
		mapOfInputValues.put(40, 1);
		mapOfInputValues.put(41, 5);
		mapOfInputValues.put(42, 8);
		mapOfInputValues.put(43, 4);
		mapOfInputValues.put(44, 9);
		
		mapOfInputValues.put(46, 5);
		mapOfInputValues.put(48, 6);
		mapOfInputValues.put(51, 1);
		mapOfInputValues.put(54, 9);
		
		mapOfInputValues.put(61, 1);
		
		mapOfInputValues.put(67, 5);
		mapOfInputValues.put(71, 4);
		mapOfInputValues.put(72, 1);
		
		mapOfInputValues.put(73, 3);
		mapOfInputValues.put(74, 1);
		mapOfInputValues.put(76, 9);
		mapOfInputValues.put(77, 4);
		mapOfInputValues.put(80, 6);
		mapOfInputValues.put(81, 8);
		
		
		// 1
		mapOfBoxPositions.put(1,1);
		mapOfBoxPositions.put(2,1);
		mapOfBoxPositions.put(3,1);
		mapOfBoxPositions.put(4,1);
		mapOfBoxPositions.put(5,2);
		mapOfBoxPositions.put(6,3);
		mapOfBoxPositions.put(7,3);
		mapOfBoxPositions.put(8,3);
		mapOfBoxPositions.put(9,3);
		// 2
		mapOfBoxPositions.put(10,1);
		mapOfBoxPositions.put(11,1);
		mapOfBoxPositions.put(12,1);
		mapOfBoxPositions.put(13,2);
		mapOfBoxPositions.put(14,2);
		mapOfBoxPositions.put(15,2);
		mapOfBoxPositions.put(16,2);
		mapOfBoxPositions.put(17,3);
		mapOfBoxPositions.put(18,3);
		// 3
		mapOfBoxPositions.put(19,1);
		mapOfBoxPositions.put(20,4);
		mapOfBoxPositions.put(21,4);
		mapOfBoxPositions.put(22,2);
		mapOfBoxPositions.put(23,5);
		mapOfBoxPositions.put(24,5);
		mapOfBoxPositions.put(25,2);
		mapOfBoxPositions.put(26,6);
		mapOfBoxPositions.put(27,3);
		// 4
		mapOfBoxPositions.put(28,1);
		mapOfBoxPositions.put(29,4);
		mapOfBoxPositions.put(30,2);
		mapOfBoxPositions.put(31,2);
		mapOfBoxPositions.put(32,5);
		mapOfBoxPositions.put(33,5);
		mapOfBoxPositions.put(34,6);
		mapOfBoxPositions.put(35,6);
		mapOfBoxPositions.put(36,3);
		// 5
		mapOfBoxPositions.put(37,7);
		mapOfBoxPositions.put(38,4);
		mapOfBoxPositions.put(39,4);
		mapOfBoxPositions.put(40,4);
		mapOfBoxPositions.put(41,5);
		mapOfBoxPositions.put(42,6);
		mapOfBoxPositions.put(43,6);
		mapOfBoxPositions.put(44,6);
		mapOfBoxPositions.put(45,3);
		// 6
		mapOfBoxPositions.put(46,7);
		mapOfBoxPositions.put(47,4);
		mapOfBoxPositions.put(48,4);
		mapOfBoxPositions.put(49,5);
		mapOfBoxPositions.put(50,5);
		mapOfBoxPositions.put(51,8);
		mapOfBoxPositions.put(52,8);
		mapOfBoxPositions.put(53,6);
		mapOfBoxPositions.put(54,9);
		// 7
		mapOfBoxPositions.put(55,7);
		mapOfBoxPositions.put(56,4);
		mapOfBoxPositions.put(57,8);
		mapOfBoxPositions.put(58,5);
		mapOfBoxPositions.put(59,5);
		mapOfBoxPositions.put(60,8);
		mapOfBoxPositions.put(61,6);
		mapOfBoxPositions.put(62,6);
		mapOfBoxPositions.put(63,9);
		// 8
		mapOfBoxPositions.put(64,7);
		mapOfBoxPositions.put(65,7);
		mapOfBoxPositions.put(66,8);
		mapOfBoxPositions.put(67,8);
		mapOfBoxPositions.put(68,8);
		mapOfBoxPositions.put(69,8);
		mapOfBoxPositions.put(70,9);
		mapOfBoxPositions.put(71,9);
		mapOfBoxPositions.put(72,9);
		// 9
		mapOfBoxPositions.put(73,7);
		mapOfBoxPositions.put(74,7);
		mapOfBoxPositions.put(75,7);
		mapOfBoxPositions.put(76,7);
		mapOfBoxPositions.put(77,8);
		mapOfBoxPositions.put(78,9);
		mapOfBoxPositions.put(79,9);
		mapOfBoxPositions.put(80,9);
		mapOfBoxPositions.put(81,9);
		
		// When
		int[][] solutionGrid = Executor2.executeForPercentGrid(mapOfInputValues, mapOfBoxPositions, solverType);
		
		// Then
		int[] expectedRow1 = {1,6,5,2,7,9,3,8,4};
		int[] expectedRow2 = {9,3,4,8,2,6,5,1,7};
		int[] expectedRow3 = {7,5,8,4,1,2,9,3,6};
		
		int[] expectedRow4 = {8,2,1,3,9,4,6,7,5};
		int[] expectedRow5 = {6,7,3,1,5,8,4,9,2};
		int[] expectedRow6 = {5,4,6,7,3,1,8,2,9};
		
		int[] expectedRow7 = {4,9,2,6,8,7,1,5,3};
		int[] expectedRow8 = {2,8,9,5,6,3,7,4,1};
		int[] expectedRow9 = {3,1,7,9,4,5,2,6,8};
		Assert.assertArrayEquals(expectedRow1, solutionGrid[0]);
		Assert.assertArrayEquals(expectedRow2, solutionGrid[1]);
		Assert.assertArrayEquals(expectedRow3, solutionGrid[2]);
		
		Assert.assertArrayEquals(expectedRow4, solutionGrid[3]);
		Assert.assertArrayEquals(expectedRow5, solutionGrid[4]);
		Assert.assertArrayEquals(expectedRow6, solutionGrid[5]);
		
		Assert.assertArrayEquals(expectedRow7, solutionGrid[6]);
		Assert.assertArrayEquals(expectedRow8, solutionGrid[7]);
		Assert.assertArrayEquals(expectedRow9, solutionGrid[8]);
		
		
	}
	
}

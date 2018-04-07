import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import logic.BuildGridFromInputsService;

public class TestBuildGridFromInputsService {

	@Test
	public void testCreateListFromInputMapForRegularSolver() {
		
		Map<Integer, Integer> mapOfPositions = new HashMap<Integer, Integer>();
		mapOfPositions.put(1, 8);
		mapOfPositions.put(4, 2);
		mapOfPositions.put(22, 6);
		mapOfPositions.put(81, 7);
		
		List<Integer> fullGrid = BuildGridFromInputsService.createListFromInputMap(mapOfPositions);
		assertEquals(82, fullGrid.size());
		assertNull(fullGrid.get(0));
		assertEquals(8, fullGrid.get(1).intValue());
		assertEquals(2, fullGrid.get(4).intValue());
		assertEquals(6, fullGrid.get(22).intValue());
		assertEquals(7, fullGrid.get(81).intValue());
	}
	
	
}

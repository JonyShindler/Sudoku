import static logic.GridAccessService.findBoxNumber;
import static org.junit.Assert.*;

import org.junit.Test;

import logic.SolverType;

public class TestGridAccessService {

	@Test
	public void testFindBoxCurrentPositionisIn() {
		assertEquals(1, findBoxNumber(3, SolverType.NORMAL, null));
		assertEquals(2, findBoxNumber(14, SolverType.NORMAL, null));
		assertEquals(3, findBoxNumber(18, SolverType.NORMAL, null));
		assertEquals(4, findBoxNumber(29, SolverType.NORMAL, null));
		assertEquals(5, findBoxNumber(49, SolverType.NORMAL, null));
		assertEquals(6, findBoxNumber(35, SolverType.NORMAL, null));
		assertEquals(7, findBoxNumber(64, SolverType.NORMAL, null));
		assertEquals(8, findBoxNumber(58, SolverType.NORMAL, null));
		assertEquals(9, findBoxNumber(81, SolverType.NORMAL, null));
	}
	
}

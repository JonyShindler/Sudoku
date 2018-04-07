package gui;

import static logic.ValidValueService.findColumnNumber;
import static logic.ValidValueService.findRowNumber;

import java.util.List;

/**
 * 
 * Service to output the solution to the console
 * @author Jony
 *
 */
public class PrinterService {

	/**
	 * Prints the entire grid to the console
	 */
	public static void printToConsole(List<Integer> solvedGrid) {
		System.out.print(" ");
		for(int counter = 1 ; counter <= 81; counter ++) {
			System.out.print(solvedGrid.get(counter));
			if (findColumnNumber(counter) == 9){
				if (findRowNumber(counter) == 3 || findRowNumber(counter) == 6){
					System.out.println();
					System.out.print(" -----------------");
				}
				System.out.println();
			}
			
			if (findColumnNumber(counter) == 3 || findColumnNumber(counter) == 6){
				System.out.print("|");
			} else {
				System.out.print(" ");
			}
			
			
		}
		System.out.println();
		System.out.println();
		System.out.println();

	}
	
}

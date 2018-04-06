package gui;

/**
 * 
 * Service to output the solution to the console
 * @author Jony
 *
 */
public class PrinterService {

	/**Prints each row to the console with a big grid lines
	 * @param row
	 */
	private static void printRowWithLines(int[] row) {
		// Take each element in the row and print it and then print a tab.
		int counter = 0;
		for (int i : row) {
			
			if (counter == 3 || counter == 6){
				System.out.print("|");
				System.out.print(i);
			} else {
				System.out.print(" ");
				System.out.print(i);
			}
			counter ++;
		}
		System.out.println();
	}
	
	
	/**Prints the entire grid to the console
	 * @param knownSafeGrid
	 */
	public static void printToConsole(int[][] knownSafeGrid) {
		int counter = 0;
		for(int[] row : knownSafeGrid) {
			if (counter == 3 ||counter ==6){
				System.out.print(" -----------------");
				System.out.println();
			}
            printRowWithLines(row);
            counter++;
		}
	}
	
}

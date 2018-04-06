package logic;

public class CreateConsecutiveGrid {

	// Create a 9x9 grid with consecutive numbers.
	    public static void createGrid() {
	        int gridOfNumbers[][]= new int[9][9];
	        int i,j,k=1;

	        for(i=0;i<9;i++) {
	            for(j=0;j<9;j++) {
	                gridOfNumbers[i][j]=k;
	                k++;
	            }
	        }

	        // for each row in gridOfNumbers, print it to console
	        for(int[] row : gridOfNumbers) {
	            printRow(row);
	        }
	    }
	    
	    
	    public static void printRow(int[] row) {
	    	// take each element in the row and print it and then print a tab.
	    	for (int i : row) {
	    		System.out.print(i);
	    		System.out.print("\t");
	    	}
	    	System.out.println();
	    }
}

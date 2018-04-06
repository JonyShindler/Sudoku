package logic;

import java.util.Map;

public class CreateBlankGrid {

	public static int[] createRowArray(Map<Integer, Integer> map) {

		int realRow[] = new int[9];
		
			int i= 1;
			for (i = 0; i < 9; i++) {
				if (map.containsKey(i)){
					realRow[i]=map.get(i);
				}
			}
		
//		printRow(realRow);
		return realRow;
	}

}

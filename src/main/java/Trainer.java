import Data.DataRow;
import Utils.Globals;

public class Trainer {
	public static void calculateResults() {
		// TODO: Implement
		for(DataRow row : Globals.TEST_ROWS) {
			row.setTotalCount((int)(Math.random() * 20));
		}
	}
}

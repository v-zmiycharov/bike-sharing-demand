package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Data.DataRow;

public class FileHelper {
	public static void setTrainRows() throws Exception {
		Globals.TRAIN_ROWS = new ArrayList<DataRow>();
		setRows(Constants.TRAIN_CSV_PATH, Globals.TRAIN_ROWS);
	}

	public static void setTestRows() throws Exception {
		Globals.TEST_ROWS = new ArrayList<DataRow>();
		setRows(Constants.TEST_CSV_PATH, Globals.TEST_ROWS);
	}
	
	private static void setRows(String path, List<DataRow> list) throws Exception {
		FileInputStream fstream = new FileInputStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		// Skip header line
		String line = br.readLine();

		while ((line = br.readLine()) != null)   {
			list.add(new DataRow(line));
		}

		//Close the input stream
		br.close();
	}

	public static void writeSubmissionResults() throws Exception {
		File outputFile = new File(Constants.SUBMISSION_CSV_PATH);
		
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
		
		bw.append("datetime,count");
		
		for(DataRow row : Globals.TEST_ROWS) {
			bw.newLine();
			bw.append(row.toSubmissionString());
		}
		
		bw.close();
	}
}

package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * Class for converting checker evaluations into chosen file formats
 * 
 * @author Nick Mazey
 *
 */
public class ResultsOutputHandler {
	/**
	 * Method for directly generating results output
	 * 
	 * @param args    - Arguments for the results output handler
	 * @param args[0] - Folder to use
	 * @param args[1] - Timestamp to use
	 * @param args[2] - File format to use (default csv)
	 */
	public static void main(String[] args) {
		if (args.length >= 3) {
			String logFolder = args[0];
			String timestamp = args[1];
			String format = args[2];
			HashMap<KnownChecker, CheckerResult> results = InvokerUtils
					.deserialiseResults(new File(logFolder + "/results" + timestamp + ".json"));
			try {
				if (format.equalsIgnoreCase("xlsx")) {
					File resultsOutputXLSX = new File(logFolder + "/resultsoutput" + timestamp + ".xlsx");
					FileOutputStream outputStream = new FileOutputStream(resultsOutputXLSX);
					XSSFWorkbook workbook = new ResultsOutputXLSX().outputResults(results);
					workbook.write(outputStream);
					workbook.close();
					outputStream.close();
					System.out.println(
							"Results XLSX available at: " + logFolder + "/resultsoutput" + timestamp + ".xlsx");

				} else {
					File resultsOutputCSV = new File(logFolder + "/resultsoutput" + timestamp + ".csv");
					FileWriter writer = new FileWriter(resultsOutputCSV);
					writer.write(new ResultsOutputCSV().outputResults(results));
					writer.close();
					System.out
							.println("Results CSV available at: " + logFolder + "/resultsoutput" + timestamp + ".csv");
				}
			} catch (IOException e) {
				System.out.println("Failed to load file");
				System.out.println("Error message: " + e.getMessage());
			}
		} else {
			System.out.println("Insufficient arguments given for ResultsOutputHandler");
		}
	}
}

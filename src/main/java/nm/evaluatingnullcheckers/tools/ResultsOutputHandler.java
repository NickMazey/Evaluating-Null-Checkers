package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.HashMap;

import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * Class for converting checker evaluations into chosen file formats
 * 
 * @author Nick Mazey
 *
 */
public class ResultsOutputHandler {

	/**
	 * Handles output by reflectively invoking formatter and outputting its data to a file
	 * @param logFolder - The folder to output to
	 * @param timestamp - The timestamp to append to the file
	 * @param format - The format to use
	 */
	public static void handleOutput(String logFolder, String timestamp, String format) {
		format = format.toUpperCase();
		HashMap<KnownChecker, CheckerResult> results = InvokerUtils
				.deserialiseResults(new File(logFolder + "/results" + timestamp + ".json"));
		String outFilePath = logFolder + "/resultsoutput" + timestamp + "." + format.toLowerCase();
		try {
			//Reflectively calls formatter
			Class<? extends ResultsOutput> outclass = (Class<? extends ResultsOutput>) Class.forName("nm.evaluatingnullcheckers.tools.ResultsOutput" + format);
			ResultsOutput o = outclass.getDeclaredConstructor().newInstance();
			Files.write(new File(outFilePath).toPath(), o.outputResults(results));
		} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
				 InvocationTargetException e) {
			throw new IllegalArgumentException("ERROR - unsupported format \"" + format + "\"");
		} catch (IOException e) {
			throw new IllegalArgumentException("ERROR - could not open file \"" + outFilePath + "\"");
		}
	}

	/**
	 * Method for invoking ResultsOutputHandler from outside Java
	 * 
	 * @param args    - Arguments for the results output handler
	 * @param args[0] - Folder to use
	 * @param args[1] - Timestamp to use
	 * @param args[2] - File format to use
	 */
	public static void main(String[] args) {
		if (args.length >= 3) {
			String logFolder = args[0];
			String timestamp = args[1];
			String format = args[2];
			try{
				handleOutput(logFolder,timestamp,format);
			} catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Usage: ResultsOutputHandler {Log Folder} {Timestamp} {Output Format}");
		}
	}

}

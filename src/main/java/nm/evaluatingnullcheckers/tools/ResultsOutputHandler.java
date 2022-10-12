package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Class for converting checker evaluations into chosen file formats
 * 
 * @author Nick Mazey
 *
 */
public class ResultsOutputHandler {

	/**
	 * Handles output by reflectively invoking formatter and outputting its data to a file
	 * @param input - The file to read from
	 * @param format - The format write output in
	 */
	public static void handleOutput(String input, String outFilePath, String format) {
		format = format.toUpperCase();
		HashMap<String, CheckerResult> results = InvokerUtils
				.deserialiseResults(new File(input));
		try {
			//Reflectively calls formatter
			Class<? extends ResultsOutput> outclass = (Class<? extends ResultsOutput>) Class.forName(ResultsOutput.class.getName() + format);
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
	 * @param args[0] - File to read from
	 * @param args[1] - File to write output to
	 * @param args[2] - Format to use
	 */
	public static void main(String[] args) {
		if (args.length >= 3) {
			String input = args[0];
			String output = args[1];
			String format = args[2];
			try{
				handleOutput(input,output,format);
			} catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Usage: ResultsOutputHandler {Input File} {Output File} {Output Format}");
		}
	}

}

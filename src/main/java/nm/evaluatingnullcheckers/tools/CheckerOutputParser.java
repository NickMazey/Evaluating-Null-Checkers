package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * CheckerOutputParser provides several static methods to parse checker logs and return if the classification
 * was positive or negative.
 * 
 * @author Nick Mazey
 *
 */
public class CheckerOutputParser {
	
	public enum CheckerOutput{
		POSITIVE,
		NEGATIVE,
		ERROR;
	}
	
	/**
	 * Parses a checkerframework log file
	 * @param file - The file to read
	 * @return - The output from the checker
	 */
	public static CheckerOutput parseCheckerFramework(File file) {
		try {
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				if(line.contains("[ERROR]")){
					reader.close();
					return CheckerOutput.ERROR;
				}
				if(line.contains("[WARNING]") && line.contains(".java")) {
					reader.close();
					return CheckerOutput.POSITIVE;
				}
			}
			reader.close();
			return CheckerOutput.NEGATIVE;
			
		} catch (FileNotFoundException e) {
			return CheckerOutput.ERROR;
		}
	}
	
	/**
	 * Parses a nullaway log file
	 * @param file - The log file to parse
	 * @return - The output from the checker
	 */
	public static CheckerOutput parseNullAway(File file) {
		try {
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				if(line.contains("[ERROR]")){
					reader.close();
					return CheckerOutput.ERROR;
				}
				if(line.contains("[WARNING]") && line.contains("[NullAway]")) {
					reader.close();
					return CheckerOutput.POSITIVE;
				}
			}
			reader.close();
			return CheckerOutput.NEGATIVE;
			
		} catch (FileNotFoundException e) {
			return CheckerOutput.ERROR;
		}
	}
	
	/**
	 * Parses an infer log file
	 * @param file - The file to be parsed
	 * @return - The output from the checker
	 */
	public static CheckerOutput parseInfer(File file) {
		try {
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				if(line.contains("error") && line.contains(".java")) {
					reader.close();
					return CheckerOutput.POSITIVE;
				}
			}
			reader.close();
			return CheckerOutput.NEGATIVE;
			
		} catch (FileNotFoundException e) {
			return CheckerOutput.ERROR;
		}
	}

	/**
	 * Main method for testing
	 */
	public static void main(String[] args) {
		
	    JFileChooser chooser = new JFileChooser();
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println(parseInfer(chooser.getSelectedFile()));
	    }
	}
}

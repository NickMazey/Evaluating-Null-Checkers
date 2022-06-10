package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * CheckerOutputParser provides several static methods to parse checker logs and
 * return if the classification was positive or negative.
 * 
 * @author Nick Mazey
 *
 */
public class CheckerOutputParser {

	public enum CheckerOutput {
		VULNERABLE, SAFE, ERROR;
	}

	public enum KnownChecker {
		CHECKERFRAMEWORK, INFER, NULLAWAY;
	}

	/**
	 * Parses a checkerframework log file
	 * 
	 * @param file - The file to read
	 * @return - The output from the checker
	 */
	public static CheckerReport parseCheckerFramework(File file) {
		if (file != null) {
			String name = file.getName();
			int i = name.lastIndexOf(".");
			if (i != -1) {
				name = name.substring(0, i);
			}
			try {
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					if (line.contains("[ERROR]")) {
						reader.close();
						return new CheckerReport(CheckerOutput.ERROR, name, line);
					}
					if (line.contains("[WARNING]") && line.contains(".java")) {
						reader.close();
						return new CheckerReport(CheckerOutput.VULNERABLE, name, line);
					}
				}
				reader.close();
				return new CheckerReport(CheckerOutput.SAFE, name, "");
			} catch (FileNotFoundException e) {
				return new CheckerReport(CheckerOutput.ERROR, name, "File Not Found");
			}
		}
		return new CheckerReport(CheckerOutput.ERROR, "N/A", "Null File");
	}

	/**
	 * Parses a nullaway log file
	 * 
	 * @param file - The log file to parse
	 * @return - The output from the checker
	 */
	public static CheckerReport parseNullAway(File file) {
		if (file != null) {
			String name = file.getName();
			int i = name.lastIndexOf(".");
			if (i != -1) {
				name = name.substring(0, i);
			}
			try {
				Scanner reader = new Scanner(file);

				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					if (line.contains("[ERROR]")) {
						reader.close();
						return new CheckerReport(CheckerOutput.ERROR, name, line);
					}
					if (line.contains("[WARNING]") && line.contains("[NullAway]")) {
						reader.close();
						return new CheckerReport(CheckerOutput.VULNERABLE, name, line);
					}
				}
				reader.close();
				return new CheckerReport(CheckerOutput.SAFE, name, "");

			} catch (FileNotFoundException e) {
				return new CheckerReport(CheckerOutput.ERROR, name, "File Not Found");
			}
		}
		return new CheckerReport(CheckerOutput.ERROR, "N/A", "Null File");
	}

	/**
	 * Parses an infer log file
	 * 
	 * @param file - The file to be parsed
	 * @return - The output from the checker
	 */
	public static CheckerReport parseInfer(File file) {
		if (file != null) {
			String name = file.getName();
			int i = name.lastIndexOf(".");
			if (i != -1) {
				name = name.substring(0, i);
			}
			CheckerOutput status = CheckerOutput.SAFE;
			String logName = file.getAbsolutePath();
			i = logName.lastIndexOf(".");
			if(i != -1) {
				logName = logName.substring(0,i);
			}
			//Checking the Maven log to ensure compilation succeeded
			File logFile = new File(logName);
			if(logFile.exists()) {
				try {
					Scanner reader = new Scanner(logFile);
					while(reader.hasNextLine()) {
						String line = reader.nextLine();
						if (line.contains("[ERROR]")) {
							reader.close();
							return new CheckerReport(CheckerOutput.ERROR, name, line);
						}
					}
				} catch (IOException e){
					//Do nothing
				}
			}
			
			StringBuilder messages = new StringBuilder();
			try {
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					if (line.contains("error") && line.contains(".java")) {
						status = CheckerOutput.VULNERABLE;
						messages.append(line);
						messages.append(System.getProperty("line.separator"));

					} else if (line.contains("warning") && line.contains(".java")) {
						status = CheckerOutput.VULNERABLE;
						messages.append(line);
						messages.append("\n");
					}
				}
				reader.close();
				return new CheckerReport(status, name, messages.toString());

			} catch (FileNotFoundException e) {
				return new CheckerReport(CheckerOutput.ERROR, name, "File Not Found");
			}
		}
		return new CheckerReport(CheckerOutput.ERROR, "N/A", "Null File");
	}

	/**
	 * Parses reports and creates a standardised report file
	 * 
	 * @param logDirectory - The folder where logs are stored
	 */
	public static HashMap<KnownChecker, ArrayList<CheckerReport>> parseReports(File logDirectory) {
		HashMap<KnownChecker, ArrayList<CheckerReport>> outputs = new HashMap<KnownChecker, ArrayList<CheckerReport>>();
		if (logDirectory != null) {
			for (File checkerFolder : logDirectory.listFiles()) {
				if (checkerFolder.isDirectory()) {
					KnownChecker checkerName = KnownChecker.valueOf(checkerFolder.getName().toUpperCase());
					ArrayList<CheckerReport> reports = new ArrayList<CheckerReport>();
					switch (checkerName) {

					case CHECKERFRAMEWORK:
						for (File log : checkerFolder.listFiles()) {
							reports.add(parseCheckerFramework(log));
						}
						outputs.put(checkerName, reports);
						break;

					case NULLAWAY:
						for (File log : checkerFolder.listFiles()) {
							reports.add(parseNullAway(log));
						}
						outputs.put(checkerName, reports);
						break;

					case INFER:
						for (File log : checkerFolder.listFiles()) {
							if (log.getName().contains(".inferreport")) {
								reports.add(parseInfer(log));
							}
						}
						outputs.put(checkerName, reports);
						break;
					}

				}
			}
		}
		return outputs;
	}

	/**
	 * Converts processed outputs into a JSON file
	 * 
	 * @param outputs - Outputs generated by parseReports
	 * @param file    - The file for writing output to
	 */
	public static void outputReportsToFile(HashMap<KnownChecker, ArrayList<CheckerReport>> outputs, File file) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String output = mapper.writeValueAsString(outputs);
			FileWriter writer = new FileWriter(file);
			writer.write(output);
			writer.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for deserialising json reports
	 * @param file - The file to deserialise
	 * @return - The report de-serialised
	 */
	public static HashMap<KnownChecker,ArrayList<CheckerReport>> deserialiseReports(File file) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<KnownChecker,ArrayList<CheckerReport>>> outputRef = new TypeReference<HashMap<KnownChecker,ArrayList<CheckerReport>>>(){};
		try {
			HashMap<KnownChecker,ArrayList<CheckerReport>> output = mapper.readValue(file, outputRef);
			return output;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<KnownChecker,ArrayList<CheckerReport>>();
	}

	/**
	 * Method for executing the output parser
	 * 
	 * @param args    - Arguments for the output parser
	 * @param args[0] - Folder to search for logs
	 * @param args[1] - File to export to
	 */
	public static void main(String[] args) {
		if (args.length >= 2) {
			File logFolder = new File(args[0]);
			File export = new File(args[1]);
			outputReportsToFile(parseReports(logFolder), export);
		}
	}
}

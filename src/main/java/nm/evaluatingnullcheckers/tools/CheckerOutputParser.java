package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import nm.evaluatingnullcheckers.tools.CheckerReport.CheckerOutput;

/**
 * CheckerOutputParser provides several static methods to parse checker logs and
 * return if the classification was positive or negative.
 * 
 * @author Nick Mazey
 *
 */
public class CheckerOutputParser {


	//Method using multiple files for parsing output
	private static CheckerReport parseFile(File file, String vulnerableRegex,String errorRegex, CheckerReport other){
		if(other.getOutput() != CheckerOutput.SAFE){
			return other;
		}
		return parseFile(file,vulnerableRegex,errorRegex);
	}

	private static CheckerReport parseFile(File file, String vulnerableRegex,String errorRegex){
		if (file != null) {
			String name = file.getName().substring(0,file.getName().lastIndexOf("."));
			CheckerOutput status = CheckerOutput.SAFE;
			StringBuilder messages = new StringBuilder();
			try {
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					if (line.matches(vulnerableRegex)) {
						status = CheckerOutput.VULNERABLE;
						messages.append(line);
						messages.append(System.getProperty("line.separator"));
					} else if(line.matches(errorRegex)){
						reader.close();
						return new CheckerReport(CheckerOutput.ERROR, name, line);
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

	private static CheckerReport auxReports(String prefix,OutputPattern pattern){
		File auxLog = new File(prefix+"."+pattern.fileExtension);
		try {
			OutputPattern auxPattern = pattern.auxiliaryPattern();
			return parseFile(auxLog,pattern.vulnerableRegex,pattern.errorRegex,auxReports(prefix,auxPattern));
		}catch (UnsupportedOperationException e){
			return parseFile(auxLog,pattern.vulnerableRegex,pattern.errorRegex);
		}
	}

	private static HashMap<String, ArrayList<CheckerReport>> parseReports(File logDirectory) {
		HashMap<String,OutputPattern> patternHashMap = InvokerUtils.getOutputPatterns();
		HashMap<String, ArrayList<CheckerReport>> outputs = new HashMap<>();
		if (logDirectory != null && logDirectory.isDirectory()) {
			for (File checkerFolder : logDirectory.listFiles()) {
				if (checkerFolder.isDirectory()) {
					String checkerName = checkerFolder.getName().toUpperCase();
					ArrayList<CheckerReport> reports = new ArrayList<>();
					OutputPattern pattern = patternHashMap.get(checkerName);
					if(pattern == null){continue;}
					for (File log : checkerFolder.listFiles()){
						if(log.getName().endsWith("."+pattern.fileExtension)){
							try{
								OutputPattern auxPattern = pattern.auxiliaryPattern();
								String logName = log.getPath().substring(0,log.getPath().length()-("." + pattern.fileExtension).length());
								reports.add(parseFile(log,pattern.vulnerableRegex,pattern.errorRegex,auxReports(logName,auxPattern)));
							} catch (UnsupportedOperationException e){
								reports.add(parseFile(log,pattern.vulnerableRegex,pattern.errorRegex));
							}

						}
					}
					outputs.put(checkerName, reports);
					}
				}
		} else {
			throw new IllegalArgumentException("ERROR - \"" +  logDirectory + "\" is not a directory or does not exist");
		}
		return outputs;
	}

	/**
	 * Method for executing the output parser directly
	 *
	 * @param args    - Arguments for the output parser
	 * @param args[0] - Folder to search for logs
	 * @param args[1] - File to export to
	 */
	public static void main(String[] args) {
		if (args.length >= 2) {
			try {
				parse(args[0], args[1]);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Usage: CheckerOutputParser {Log Folder} {Output File}");
		}
	}

	/**
	 * Method for executing the output parser from another class
	 *
	 * @param logFolder - Log folder to read from
	 * @param export - File to export to
	 */
	public static void parse(String logFolder, String export) {
		InvokerUtils.outputReportsToFile(parseReports(new File(logFolder)), new File(export));
	}
}

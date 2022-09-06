package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import nm.evaluatingnullcheckers.tools.InvokerUtils.CheckerOutput;
import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * CheckerOutputParser provides several static methods to parse checker logs and
 * return if the classification was positive or negative.
 * 
 * @author Nick Mazey
 *
 */
public class CheckerOutputParser {

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
			long time = getTime(file);
			try {
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					if (line.contains("[ERROR]")) {
						reader.close();
						return new CheckerReport(CheckerOutput.ERROR, name, line, time);
					}
					if (line.contains("[WARNING]") && line.contains(".java")) {
						reader.close();
						return new CheckerReport(CheckerOutput.VULNERABLE, name, line, time);
					}
				}
				reader.close();
				return new CheckerReport(CheckerOutput.SAFE, name, "", time);
			} catch (FileNotFoundException e) {
				return new CheckerReport(CheckerOutput.ERROR, name, "File Not Found", time);
			}
		}
		return new CheckerReport(CheckerOutput.ERROR, "N/A", "Null File", 0);
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
			long time = getTime(file);
			try {
				Scanner reader = new Scanner(file);

				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					if (line.contains("[ERROR]")) {
						reader.close();
						return new CheckerReport(CheckerOutput.ERROR, name, line, time);
					}
					if (line.contains("[WARNING]") && line.contains("[NullAway]")) {
						reader.close();
						return new CheckerReport(CheckerOutput.VULNERABLE, name, line, time);
					}
				}
				reader.close();
				return new CheckerReport(CheckerOutput.SAFE, name, "", time);

			} catch (FileNotFoundException e) {
				return new CheckerReport(CheckerOutput.ERROR, name, "File Not Found", time);
			}
		}
		return new CheckerReport(CheckerOutput.ERROR, "N/A", "Null File", 0);
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
			long time = getTime(file);
			CheckerOutput status = CheckerOutput.SAFE;
			String logName = file.getAbsolutePath();
			i = logName.lastIndexOf(".");
			if (i != -1) {
				logName = logName.substring(0, i);
			}
			// Checking the Maven log to ensure compilation succeeded
			File logFile = new File(logName + ".log");
			if (logFile.exists()) {
				try {
					Scanner reader = new Scanner(logFile);
					while (reader.hasNextLine()) {
						String line = reader.nextLine();
						if (line.contains("[ERROR]")) {
							reader.close();
							return new CheckerReport(CheckerOutput.ERROR, name, line, time);
						}
					}
					reader.close();
				} catch (IOException e) {
					// Do nothing
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
				return new CheckerReport(status, name, messages.toString(), time);

			} catch (FileNotFoundException e) {
				return new CheckerReport(CheckerOutput.ERROR, name, "File Not Found", time);
			}
		}
		return new CheckerReport(CheckerOutput.ERROR, "N/A", "Null File", 0);
	}

	private static long getTime(File file) {
		long time = 0;
		String timeName = file.getAbsolutePath();
		int i = timeName.lastIndexOf(".");
		if (i != -1) {
			timeName = timeName.substring(0, i);
		}

		File timeFile = new File(timeName + ".time");
		if (timeFile.exists()) {
			try {
				Scanner reader = new Scanner(timeFile);
				if (reader.hasNextLine()) {
					time = Long.valueOf(reader.nextLine());
				}
				reader.close();
			} catch (IOException e) {
				// Do nothing
			}
		}
		return time;
	}

	/**
	 * Parses reports and creates a standardised report file in JSON format
	 *
	 * @param logDirectory - The folder where logs are stored
	 */
	public static HashMap<KnownChecker, ArrayList<CheckerReport>> parseReports(File logDirectory) {
		HashMap<KnownChecker, ArrayList<CheckerReport>> outputs = new HashMap<KnownChecker, ArrayList<CheckerReport>>();
		if (logDirectory != null && logDirectory.isDirectory()) {
			for (File checkerFolder : logDirectory.listFiles()) {
				if (checkerFolder.isDirectory()) {
					KnownChecker checkerName = KnownChecker.valueOf(checkerFolder.getName().toUpperCase());
					ArrayList<CheckerReport> reports = new ArrayList<CheckerReport>();
					switch (checkerName) {

						case CHECKERFRAMEWORK:
							for (File log : checkerFolder.listFiles()) {
								if (!log.getName().contains(".time")) {
									reports.add(parseCheckerFramework(log));
								}
							}
							outputs.put(checkerName, reports);
							break;

						case NULLAWAY:
							for (File log : checkerFolder.listFiles()) {
								if (!log.getName().contains(".time")) {
									reports.add(parseNullAway(log));
								}
							}
							outputs.put(checkerName, reports);
							break;

						case INFER:
							for (File log : checkerFolder.listFiles()) {
								if (!log.getName().contains(".time")) {
									if (log.getName().contains(".inferreport")) {
										reports.add(parseInfer(log));
									}
								}
							}
							outputs.put(checkerName, reports);
							break;
					}

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

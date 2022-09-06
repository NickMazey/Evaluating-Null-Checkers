package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * CLI invoker for the checker benchmarking process
 * 
 * @author Nick Mazey
 *
 */
public class BenchmarkInvokerCLI {
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
	
	/**
	 * Invoker for the benchmark suite in Java
	 * 
	 * @param args[0] - File containing a list of subject programs to test
	 * @param args[1] - File containing a list of checkers to execute
	 * @param args[2] - Format to use (such as xlsx, csv,... defaults to csv)
	 * @param printStream - The printstream for printing output to
	 */
	public static void auxilliaryMain(String[] args,PrintStream printStream) {
		if (args.length >= 2) {
			String timestamp = format.format(new Timestamp(new Date().getTime()));
			String logFolder = "log/log" + timestamp;
			Process p;
			try {
				p = new ProcessBuilder(System.getProperty("user.dir") + "/run.sh", "-b " + args[0], "-c " + args[1],
						"-l " + logFolder).inheritIO().start();
				p.waitFor();
				printStream.println("Raw checker output available at: " + logFolder + "/checkeroutput");

				// Re-compiling tools because checkers use mvn clean
				InvocationRequest request = new DefaultInvocationRequest();
				request.setPomFile(new File(System.getProperty("user.dir") + "/pom.xml"));
				request.setGoals(Arrays.asList("clean", "compile"));
				request.addArg("-P compiletools");
				request.addArg("-q");
				request.setBatchMode(true);
				Invoker invoker = new DefaultInvoker();
				invoker.execute(request);

				CheckerOutputParser.parse(logFolder, logFolder + "/checkeroutput" + timestamp + ".json");
				printStream.println("Parsed checker output available at: " + logFolder + "/checkeroutput" + timestamp + ".json");
				CheckerEvaluator.evaluate(logFolder + "/checkeroutput" + timestamp + ".json",logFolder + "/results" + timestamp + ".json");
				printStream.println("Evaluator output available at: " + logFolder + "/results" + timestamp + ".json");
				String format = "csv";
				if(args.length >= 3) {
					format = args[2];
				}
				ResultsOutputHandler.handleOutput(logFolder,timestamp,format);
			} catch (IOException e) {
				printStream.println("Failed to load file");
				printStream.println("Error message: " + e.getMessage());
			}catch (MavenInvocationException e) {
				printStream.println("Tool compilation encountered an error");
				printStream.println("Error message: " + e.getMessage());
			} catch (InterruptedException e) {
				printStream.println("Thread interrupted unexpectedly");
				printStream.println("Error message: " + e.getMessage());
			}
		} else {
			printStream.println(
					"Usage: BenchmarkInvokerCLI {Subject List File} {Checker List File} {Result File Format [default - csv]}");
		}
	}
	
	
	public static void main(String[] args) {
		auxilliaryMain(args,System.out);
		
	}

}

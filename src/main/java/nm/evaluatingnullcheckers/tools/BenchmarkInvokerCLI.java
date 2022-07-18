package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
	 */
	public static void main(String[] args) {

		if (args.length >= 2) {
			String timestamp = format.format(new Timestamp(new Date().getTime()));
			String logFolder = "log/log" + timestamp;
			Process p;
			try {
				p = new ProcessBuilder(System.getProperty("user.dir") + "/run.sh", "-b " + args[0], "-c " + args[1],
						"-l " + logFolder).inheritIO().start();
				p.waitFor();
				System.out.println("Raw checker output available at: " + logFolder + "/checkeroutput");

				// Re-compiling tools because checkers use mvn clean
				InvocationRequest request = new DefaultInvocationRequest();
				request.setPomFile(new File(System.getProperty("user.dir") + "/pom.xml"));
				request.setGoals(Arrays.asList("clean", "compile"));
				request.addArg("-P compiletools");
				request.addArg("-q");
				request.setBatchMode(true);
				Invoker invoker = new DefaultInvoker();
				invoker.execute(request);

				String[] parserArgs = { logFolder, logFolder + "/checkeroutput" + timestamp + ".json" };
				CheckerOutputParser.main(parserArgs);
				System.out.println(
						"Parsed checker output available at: " + logFolder + "/checkeroutput" + timestamp + ".json");
				String[] evaluatorArgs = { logFolder + "/checkeroutput" + timestamp + ".json",
						logFolder + "/results" + timestamp + ".json" };
				CheckerEvaluator.main(evaluatorArgs);
				System.out.println("Evaluator output available at: " + logFolder + "/results" + timestamp + ".json");
				String[] resultsOutputArgs = {logFolder,timestamp,".csv"};
				if(args.length >= 3) {
					resultsOutputArgs[2] = args[2];
				}
				ResultsOutputHandler.main(resultsOutputArgs);
			} catch (IOException e) {
				System.out.println("Failed to load file");
				System.out.println("Error message: " + e.getMessage());
			}catch (MavenInvocationException e) {
				System.out.println("Tool compilation encountered an error");
				System.out.println("Error message: " + e.getMessage());
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted unexpectedly");
				System.out.println("Error message: " + e.getMessage());
			}
		} else {
			System.out.println(
					"Usage: BenchmarkInvokerCLI {Subject List File} {Checker List File} {Result File Format [default - csv]}");
		}
	}

}

package nm.evaluatingnullcheckers.tools;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

/**
 * CLI invoker for the checker benchmarking process
 *
 * @author Nick Mazey
 */
public class BenchmarkInvokerCLI {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    /**
     * Method for calling benchmark invoker from another class
     *
     * @param subjectFile - File containing a list of subject programs
     * @param checkerFile - File containing a list of checkers to evaluate
     * @param logFolder - Folder to output logs to
     * @param timestamp - Timestamp for file names
     * @param format - Format to convert results to (null if results should not be formatted)
     * @param printStream - Stream to print output to
     */
    public static void auxilliaryMain(String subjectFile, String checkerFile, String logFolder, String timestamp, String format, PrintStream printStream) {
        Process p;
        try {
            p = new ProcessBuilder(System.getProperty("user.dir") + "/run.sh", "-b " + subjectFile, "-c " + checkerFile,
                    "-l " + logFolder).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            try {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    printStream.println(line);
                }
            } catch (IOException e) {
                printStream.println("ERROR - Could not read shell script");
                printStream.println("Message: " + e.getMessage());
            }
            p.waitFor();
            printStream.println("Raw checker output available at: " + logFolder);

            // Re-compiling tools because checkers use mvn clean
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File(System.getProperty("user.dir") + "/pom.xml"));
            request.setGoals(Arrays.asList("clean", "compile"));
            request.addArg("-P compiletools");
            request.addArg("-q");
            request.setBatchMode(true);
            Invoker invoker = new DefaultInvoker();
            invoker.execute(request);
            String parsedOutputFile = logFolder + "/checkeroutput" + timestamp + ".json";
            CheckerOutputParser.parse(logFolder, parsedOutputFile);
            printStream.println("Parsed checker output available at: " +parsedOutputFile);
            String evaluatedOutputFile = logFolder + "/results" + timestamp + ".json";
            CheckerEvaluator.evaluate(parsedOutputFile, evaluatedOutputFile);
            printStream.println("Evaluator output available at: " + evaluatedOutputFile);
			if(format != null) {
                String formatFile = logFolder + "/results" + timestamp + "." + format.toLowerCase();
				ResultsOutputHandler.handleOutput(evaluatedOutputFile, formatFile, format);
				printStream.println("Results output available at: " + formatFile);
			}

        } catch (IOException e) {
            printStream.println("Failed to load file");
            printStream.println("Error message: " + e.getMessage());
        } catch (MavenInvocationException e) {
            printStream.println("Tool compilation encountered an error");
            printStream.println("Error message: " + e.getMessage());
        } catch (InterruptedException e) {
            printStream.println("Thread interrupted unexpectedly");
            printStream.println("Error message: " + e.getMessage());
        }
    }


    /**
     * CLI-based benchmark invoker
     *
     * @param args[0] - File containing a list of subject programs to test
     * @param args[1] - File containing a list of checkers to execute
     * @param args[2] - Format to use (such as xlsx, csv,... defaults to csv)
     */
    public static void main(String[] args) {
        String timestamp = format.format(new Timestamp(new Date().getTime()));
        String logFolder = "log/log" + timestamp;
        if (args.length >= 2) {
            //Default format
            String format = "csv";
            if (args.length >= 3) {
                format = args[2];
            }
            try {
                auxilliaryMain(args[0], args[1],logFolder, timestamp, format,System.out);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(
                    "Usage: BenchmarkInvokerCLI {Subject List File} {Checker List File} {Result File Format [default - csv]}");
        }
    }

}

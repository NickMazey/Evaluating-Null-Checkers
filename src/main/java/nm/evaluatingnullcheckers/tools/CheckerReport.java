package nm.evaluatingnullcheckers.tools;

import nm.evaluatingnullcheckers.tools.InvokerUtils.CheckerOutput;

/**
 * Class for storing checker output
 * 
 * @author Nick Mazey
 *
 */
public class CheckerReport {

	private CheckerOutput output;
	private String message;
	private String subjectName;
	private long executionTime;

	/**
	 * Class for storing output from the checkers
	 * 
	 * @param output        - How the checker classified the subject program
	 * @param message       - The error detected by the checker
	 * @param subjectName   - The name of the subject
	 * @param executionTime - How long the subject took to execute (ms)
	 */
	public CheckerReport(CheckerOutput output, String subjectName, String message, long executionTime) {
		this.output = output;
		this.subjectName = subjectName;
		this.message = message;
		this.executionTime = executionTime;
	}

	/**
	 * Default cstor for deserialisation
	 */
	public CheckerReport() {
	}

	/**
	 * Getter for output
	 * 
	 * @return - This report's output
	 */
	public CheckerOutput getOutput() {
		return output;
	}

	/**
	 * Setter for output
	 * 
	 * @param output - The output to set
	 */
	public void setOutput(CheckerOutput output) {
		this.output = output;
	}

	/**
	 * Getter for the subject name
	 * 
	 * @return - The name of the subject
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Setter for subject name
	 * 
	 * @param subjectName - The name to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Getter for message
	 * 
	 * @return - This report's message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message
	 * 
	 * @param message - Message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Getter for execution time
	 * 
	 * @return - execution time for this subject
	 */
	public long getExecutionTime() {
		return this.executionTime;
	}
	
	/**
	 * Setter for execution time
	 * @param executionTime - execution time to set
	 */
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
}

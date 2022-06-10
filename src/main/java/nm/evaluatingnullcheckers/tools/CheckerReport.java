package nm.evaluatingnullcheckers.tools;

import nm.evaluatingnullcheckers.tools.CheckerOutputParser.CheckerOutput;

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

	/**
	 * Class for storing output from the checkers
	 * 
	 * @param output      - How the checker classified the subject program
	 * @param message     - The error detected by the checker
	 * @param subjectName - The name of the subject
	 */
	public CheckerReport(CheckerOutput output, String subjectName, String message) {
		this.output = output;
		this.subjectName = subjectName;
		this.message = message;
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
	 * Getter for the subject name
	 * 
	 * @return - The name of the subject
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Getter for message
	 * 
	 * @return - This report's message
	 */
	public String getMessage() {
		return message;
	}
}

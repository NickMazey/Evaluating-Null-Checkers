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
	/**
	 * Class for storing output from the checkers
	 * 
	 * @param output        - How the checker classified the subject program
	 * @param message       - The error detected by the checker
	 * @param subjectName   - The name of the subject
	 */
	public CheckerReport(CheckerOutput output, String subjectName, String message) {
		this.output = output;
		this.subjectName = subjectName;
		this.message = message;
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

}

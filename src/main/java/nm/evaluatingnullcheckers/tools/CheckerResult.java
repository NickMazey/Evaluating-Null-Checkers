package nm.evaluatingnullcheckers.tools;

import java.util.HashMap;

import nm.evaluatingnullcheckers.tools.InvokerUtils.Flag;


/**
 * Class to store the result from a checker
 * 
 * @author Nick Mazey
 *
 */
public class CheckerResult {

	private double precision;
	private double recall;
	private long executionTime;
	private HashMap<String, Flag> subjectResults;
	private HashMap<String, String> subjectMessages;

	/**
	 * Class to store data regarding an evaluated checker
	 * 
	 * @param precision       - The checker's precision
	 * @param recall          - The checker's recall
	 * @param executionTime   - How long the checker took (in milliseconds) to
	 *                        execute the subjects
	 * @param subjectResults  - Map from subject names to results
	 * @param subjectMessages - Map from subject names to messages
	 */
	public CheckerResult(double precision, double recall, long executionTime, HashMap<String, Flag> subjectResults,
			HashMap<String, String> subjectMessages) {
		this.precision = precision;
		this.recall = recall;
		this.subjectResults = subjectResults;
		this.executionTime = executionTime;
		this.subjectMessages = subjectMessages;
	}

	/**
	 * Default constructor for deserialisation
	 */
	public CheckerResult() {

	}

	/**
	 * Getter for precision
	 * 
	 * @return - The precision of this checker
	 */
	public double getPrecision() {
		return precision;
	}

	/**
	 * Setter for precision
	 * 
	 * @param precision - The value to set precision to
	 */
	public void setPrecision(double precision) {
		this.precision = precision;
	}

	/**
	 * Getter for recall
	 * 
	 * @return - This checker's recall
	 */
	public double getRecall() {
		return recall;
	}

	/**
	 * Setter for recall
	 * 
	 * @param recall - The value to set recall to
	 */
	public void setRecall(double recall) {
		this.recall = recall;
	}

	/**
	 * Getter for execution time
	 * 
	 * @return - The checker's execution time (in milliseconds)
	 */
	public long getExecutionTime() {
		return executionTime;
	}

	/**
	 * Setter for execution time
	 * 
	 * @param executionTime - The execution time to set (in milliseconds)
	 */
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * Getter for subject results
	 * 
	 * @return - Map from subject names to result from the evaluator
	 */
	public HashMap<String, Flag> getSubjectResults() {
		return subjectResults;
	}

	/**
	 * Setter for subject results
	 * 
	 * @param subjectResults - Map from subject names to result from the evaluator
	 */
	public void setSubjectResults(HashMap<String, Flag> subjectResults) {
		this.subjectResults = subjectResults;
	}

	/**
	 * Getter for subject messages
	 * 
	 * @return - Map from subject names to messages
	 */
	public HashMap<String, String> getSubjectMessages() {
		return subjectMessages;
	}

	/**
	 * Setter for subject messages
	 * 
	 * @param subjectMessages - Map from subject names to messages
	 */
	public void setSubjectMessages(HashMap<String, String> subjectMessages) {
		this.subjectMessages = subjectMessages;
	}
}

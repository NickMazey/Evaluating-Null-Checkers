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
	private double accuracy;
	private HashMap<String, Flag> subjectResults;
	private HashMap<String, String> subjectMessages;
	private HashMap<String,Double> similarity;

	/**
	 * Class to store data regarding an evaluated checker
	 * 
	 * @param precision             - The checker's precision
	 * @param recall                - The checker's recall
	 * @param accuracy              - The checker's accuracy
	 * @param subjectResults        - Map from subject names to results
	 * @param subjectMessages       - Map from subject names to messages
	 */
	public CheckerResult(double precision, double recall, double accuracy, HashMap<String, Flag> subjectResults,
			HashMap<String, String> subjectMessages) {
		this.precision = precision;
		this.recall = recall;
		this.accuracy = accuracy;
		this.subjectResults = subjectResults;
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
	 * Getter for accuracy
	 * 
	 * @return - The checker's accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}
	
	/**
	 * Setter for accuracy
	 * 
	 * @param accuracy - Value to set accuracy to
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
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

	
	/**
	 * Getter for jaccard similarity
	 * 
	 * @return - Mapping from other checkers to their jaccard similarity
	 */
	public HashMap<String, Double> getSimilarity(){
		return similarity;
	}
	
	/**
	 * Setter for jaccard similarity
	 * 
	 * @param similarity - Mapping from other checkers to their jaccard similarity
	 */
	public void setSimilarity(HashMap<String,Double> similarity) {
		this.similarity=similarity;
	}
}

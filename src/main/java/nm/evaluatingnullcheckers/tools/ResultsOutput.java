package nm.evaluatingnullcheckers.tools;

import java.util.HashMap;

import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * Interface for creating results output generators
 * 
 * @author Nick Mazey
 *
 */
public interface ResultsOutput {
	
	/**
	 * Method for outputting results from the checker evaluator to an object of type T
	 * 
	 * @param results - Results from the checker evaluator
	 * @return - The results presented in a format related to the concrete class
	 */
	byte[] outputResults(HashMap<KnownChecker, CheckerResult> results);
	
	
}

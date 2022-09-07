package nm.evaluatingnullcheckers.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * Produces results output as a string in CSV format
 * 
 * @author Nick Mazey
 *
 */
public class ResultsOutputCSV implements ResultsOutput {

	@Override
	public byte[] outputResults(HashMap<KnownChecker, CheckerResult> results) {
		//Specifying top left cell as empty
		StringBuilder csvStr = new StringBuilder("\"\"");
		
		ArrayList<String> subjects = InvokerUtils.getSubjectsFromResults(results);
		ArrayList<KnownChecker> checkersInOrder = new ArrayList<>(results.keySet());
 		Collections.sort(checkersInOrder);
 		
 		//First line
 		for(KnownChecker checker : checkersInOrder) {
 			csvStr.append(",\"").append(checker).append("\"");
 		}
 		csvStr.append("\n");
 		
 		//Metrics
 		csvStr.append("\"Precision\"");
 		for(KnownChecker checker : checkersInOrder) {
 			CheckerResult result = results.get(checker);
 			csvStr.append(",\"").append(result.getPrecision()).append("\"");
 		}
 		csvStr.append("\n");
 		csvStr.append("\"Recall\"");
 		for(KnownChecker checker : checkersInOrder) {
 			CheckerResult result = results.get(checker);
 			csvStr.append(",\"").append(result.getRecall()).append("\"");
 		}
 		csvStr.append("\n");
 		csvStr.append("\"Execution Time\"");
 		for(KnownChecker checker : checkersInOrder) {
 			CheckerResult result = results.get(checker);
 			csvStr.append(",\"").append(result.getExecutionTime()).append("\"");
 		}
 		csvStr.append("\n");
 		csvStr.append("\"Subject Results: \" \n");
 		
 		//Subject Results
 		for(String subject : subjects) {
 			csvStr.append("\"").append(subject).append("\"");
 			for(KnownChecker checker : checkersInOrder) {
 				CheckerResult result = results.get(checker);
 				csvStr.append(",\"").append(result.getSubjectResults().get(subject)).append("\"");
 			}
 			csvStr.append("\n");
 		}
 		csvStr.append("\"Subject Messages: \" \n");
 		//Subject Messages
 		for(String subject : subjects) {
 			csvStr.append("\"").append(subject).append("\"");
 			for(KnownChecker checker : checkersInOrder) {
 				CheckerResult result = results.get(checker);
 				csvStr.append(",\"").append(result.getSubjectMessages().get(subject)).append("\"");
 			}
 			csvStr.append("\n");
 		}
 		csvStr.append("\"Subject Execution Times: \" \n");
 		//Subject Execution Times
 		for(String subject : subjects) {
 			csvStr.append("\"").append(subject).append("\"");
 			for(KnownChecker checker : checkersInOrder) {
 				CheckerResult result = results.get(checker);
 				csvStr.append(",\"").append(result.getSubjectExecutionTimes().get(subject)).append("\"");
 			}
 			csvStr.append("\n");
 		}
		return csvStr.toString().getBytes();
	}
}

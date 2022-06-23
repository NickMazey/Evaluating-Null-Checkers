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
public class ResultsOutputCSV implements ResultsOutput<String> {

	@Override
	public String outputResults(HashMap<KnownChecker, CheckerResult> results) {
		//Specifying top left cell as empty
		StringBuilder csvStr = new StringBuilder("\"\"");
		
		ArrayList<String> subjects = new ArrayList<String>();
		for (CheckerResult checkerResult : results.values()) {
			for(String subject : checkerResult.getSubjectResults().keySet()) {
				if(!subjects.contains(subject)) {
					subjects.add(subject);
				}
			}
		}
		ArrayList<KnownChecker> checkersInOrder = new ArrayList<KnownChecker>();
 		for (KnownChecker checker : results.keySet()) {
 			checkersInOrder.add(checker);
		}
 		Collections.sort(checkersInOrder);
 		
 		//First line
 		for(KnownChecker checker : checkersInOrder) {
 			csvStr.append(",\"" + checker + "\"");
 		}
 		csvStr.append("\n");
 		
 		//Metrics
 		csvStr.append("\"Precision\"");
 		for(KnownChecker checker : checkersInOrder) {
 			CheckerResult result = results.get(checker);
 			csvStr.append(",\"" + result.getPrecision() + "\"");
 		}
 		csvStr.append("\n");
 		csvStr.append("\"Recall\"");
 		for(KnownChecker checker : checkersInOrder) {
 			CheckerResult result = results.get(checker);
 			csvStr.append(",\"" + result.getRecall() + "\"");
 		}
 		csvStr.append("\n");
 		csvStr.append("\"Execution Time\"");
 		for(KnownChecker checker : checkersInOrder) {
 			CheckerResult result = results.get(checker);
 			csvStr.append(",\"" + result.getExecutionTime() + "\"");
 		}
 		csvStr.append("\n");
 		csvStr.append("\"Subject Results: \" \n");
 		
 		//Subject Results
 		for(String subject : subjects) {
 			csvStr.append("\""+subject+"\"");
 			for(KnownChecker checker : checkersInOrder) {
 				CheckerResult result = results.get(checker);
 				csvStr.append(",\"" + result.getSubjectResults().get(subject) + "\"");
 			}
 			csvStr.append("\n");
 		}
 		csvStr.append("\"Subject Messages: \" \n");
 		//Subject Messages
 		for(String subject : subjects) {
 			csvStr.append("\""+subject+"\"");
 			for(KnownChecker checker : checkersInOrder) {
 				CheckerResult result = results.get(checker);
 				csvStr.append(",\"" + result.getSubjectMessages().get(subject) + "\"");
 			}
 			csvStr.append("\n");
 		}
		return csvStr.toString();
	}
}

package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.tools.InvokerUtils.CheckerOutput;
import nm.evaluatingnullcheckers.tools.InvokerUtils.Flag;
import nm.evaluatingnullcheckers.tools.InvokerUtils.KnownChecker;

/**
 * Evaluator for the null checkers Reads output from the parser and creates
 * results
 * 
 * @author Nick Mazey
 *
 */
public class CheckerEvaluator {

	/**
	 * Method that finds all unique subjects in the outputs, designed to enable
	 * metadata caching
	 * 
	 * @param output - Output produced by the CheckerOutputParser
	 * @return - A list of unique subjects found in the outputs
	 */
	public static ArrayList<String> getSubjects(HashMap<KnownChecker, ArrayList<CheckerReport>> output) {
		ArrayList<String> names = new ArrayList<String>();
		if (output != null) {
			for (ArrayList<CheckerReport> reports : output.values()) {
				for (CheckerReport report : reports) {
					if (!names.contains(report.getSubjectName())) {
						names.add(report.getSubjectName());
					}
				}
			}
		}
		return names;
	}

	/**
	 * Using the metadata cache, constructs an expected output cache for known
	 * subjects
	 * 
	 * @param metadata - The pre-computed metadata
	 * @return - A mapping from subjects to expected classifications
	 */
	public static HashMap<String, CheckerOutput> getExpectedOutput(HashMap<String, ArrayList<Annotation>> metadata) {
		HashMap<String, CheckerOutput> expectedOutputs = new HashMap<String, CheckerOutput>();
		if (metadata != null) {
			for (String subjectName : metadata.keySet()) {
				for (Annotation annotation : metadata.get(subjectName)) {
					for (Annotation superAnnotation : annotation.annotationType().getAnnotations()) {
						if (superAnnotation.annotationType().equals(BenchmarkAnnotations.ExpectedNPE.class)) {
							if (annotation.annotationType().equals(BenchmarkAnnotations.NPEProne.class)) {
								expectedOutputs.put(subjectName, CheckerOutput.VULNERABLE);
							} else if (annotation.annotationType().equals(BenchmarkAnnotations.NPEProof.class)) {
								expectedOutputs.put(subjectName, CheckerOutput.SAFE);
							}
						}
					}
				}
			}
		}
		return expectedOutputs;
	}

	/**
	 * Computes precision of a checker
	 * 
	 * @param results - Collection of flags from a checker
	 * @return - Precision value computed using these flags (TP / (TP + FP))
	 */
	public static double calculatePrecision(Collection<Flag> results) {
		double truePositives = 0;
		double falsePositives = 0;
		for (Flag result : results) {
			switch (result) {
			case TRUEPOSITIVE:
				truePositives++;
				break;
			case FALSEPOSITIVE:
				falsePositives++;
				break;
			default:
				break;
			}
		}
		if (truePositives + falsePositives > 0) {
			return truePositives / (falsePositives + truePositives);
		} else {
			return 0;
		}
	}

	/**
	 * Computes recall of a checker
	 * 
	 * @param results - Collection of flags From a checker
	 * @return - Recall value computed using these flags (TP / (TP + FN))
	 */
	public static double calculateRecall(Collection<Flag> results) {
		double truePositives = 0;
		double falseNegatives = 0;
		for (Flag result : results) {
			switch (result) {
			case TRUEPOSITIVE:
				truePositives++;
				break;
			case FALSENEGATIVE:
				falseNegatives++;
				break;
			default:
				break;
			}
		}
		if (truePositives + falseNegatives > 0) {
			return truePositives / (falseNegatives + truePositives);
		} else {
			return 0;
		}
	}
	
	/**
	 * Computes accuracy of a checker
	 * 
	 * @param results - Collection of flags From a checker
	 * @return - Accuracy value computed using these flags ((TP + TN) / (TP + TN + FP + FN))
	 */
	public static double calculateAccuracy(Collection<Flag> results) {
		double truePositives = 0;
		double trueNegatives = 0;
		double falsePositives = 0;
		double falseNegatives = 0;
		for (Flag result : results) {
			switch (result) {
			case TRUEPOSITIVE:
				truePositives++;
				break;
			case TRUENEGATIVE:
				trueNegatives++;
				break;
			case FALSEPOSITIVE:
				falsePositives++;
				break;
			case FALSENEGATIVE:
				falseNegatives++;
				break;
			default:
				break;
			}
		}
		if (truePositives + trueNegatives + falsePositives + falseNegatives > 0) {
			return (truePositives + trueNegatives) / (truePositives + trueNegatives + falsePositives + falseNegatives);
		} else {
			return 0;
		}
	}

	/**
	 * Method for assessing checker output
	 * 
	 * @param expectedOutput - The output expected from the checker report
	 * @param reportOutput   - The output the checker report gave
	 * @return - Flag corresponding to checker accuracy
	 */
	public static Flag getFlag(CheckerOutput expectedOutput, CheckerOutput reportOutput) {
		if (reportOutput != CheckerOutput.ERROR) {
			boolean correct = expectedOutput == reportOutput;
			switch (expectedOutput) {
			case VULNERABLE:
				if (correct) {
					return Flag.TRUEPOSITIVE;
				} else {
					return Flag.FALSENEGATIVE;
				}
			case SAFE:
				if (correct) {
					return Flag.TRUENEGATIVE;
				} else {
					return Flag.FALSEPOSITIVE;
				}
			default:
				return Flag.ERROR;
			}

		} else {
			return Flag.ERROR;
		}
	}

	/**
	 * Evaluates checkers and produces a standardised result
	 * 
	 * @param output - Output from CheckerOutputParser
	 * @return - Map from checkers to results
	 */
	public static HashMap<KnownChecker, CheckerResult> evaluateCheckers(
			HashMap<KnownChecker, ArrayList<CheckerReport>> output) {
		ArrayList<String> names = getSubjects(output);
		HashMap<String, ArrayList<Annotation>> metadata = InvokerUtils.getMetadata(names);
		HashMap<String, CheckerOutput> expectedOutputs = getExpectedOutput(metadata);
		HashMap<KnownChecker, CheckerResult> results = new HashMap<KnownChecker, CheckerResult>();
		if (output != null) {
			for (KnownChecker checker : output.keySet()) {
				long totalTime = 0;
				HashMap<String, Flag> subjectResults = new HashMap<String, Flag>();
				HashMap<String, String> subjectMessages = new HashMap<String, String>();
				HashMap<String, Long> subjectExecutionTimes = new HashMap<String, Long>();
				for (CheckerReport report : output.get(checker)) {
					String subjectName = report.getSubjectName();
					CheckerOutput expectedOutput = expectedOutputs.get(subjectName);
					CheckerOutput reportOutput = report.getOutput();
					subjectResults.put(subjectName, getFlag(expectedOutput, reportOutput));
					subjectMessages.put(subjectName, report.getMessage());
					subjectExecutionTimes.put(subjectName, report.getExecutionTime());
					totalTime += report.getExecutionTime();
				}
				double precision = calculatePrecision(subjectResults.values());
				double recall = calculateRecall(subjectResults.values());
				double accuracy = calculateAccuracy(subjectResults.values());
				results.put(checker, new CheckerResult(precision, recall,accuracy, totalTime, subjectResults, subjectMessages,subjectExecutionTimes));
			}
			for (KnownChecker checker : results.keySet()) {
				results.get(checker).setSimilarity(computeSimilarities(checker,results));
			}
		}
		return results;
	}
	
	private static HashMap<KnownChecker, Double> computeSimilarities(KnownChecker checker, HashMap<KnownChecker,CheckerResult> results){
			HashMap<KnownChecker,Double> similarity = new HashMap<KnownChecker,Double>();
			HashMap<String,Flag> subjectResults = results.get(checker).getSubjectResults();
			for (KnownChecker other : results.keySet()) {
				if(other != checker) {
					HashMap<String,Flag> otherSubjectResults = results.get(other).getSubjectResults();
					double total = subjectResults.size() + otherSubjectResults.size();
					double union = 0;
					HashSet<String> allSubjects = new HashSet<String>();
					allSubjects.addAll(subjectResults.keySet());
					allSubjects.addAll(otherSubjectResults.keySet());
					for(String subjectName : allSubjects) {
						if(subjectResults.containsKey(subjectName) && otherSubjectResults.containsKey(subjectName)) {
							if(subjectResults.get(subjectName).equals(otherSubjectResults.get(subjectName))) {
								union++;
							}
						}
					}
				    double index = 0;
				    if(total-union != 0) {
				    	index = union/(total-union);
				    }
				   similarity.put(other, index);
				}
			}
			return similarity;
	}

	/**
	 * Method for executing the checker evaluator directly
	 * 
	 * @param args    - Arguments for the evaluator
	 * @param args[0] - Report file in JSON format
	 * @param args[1] - File to export to
	 */
	public static void main(String args[]) {
		evaluate(args);
	}
	
	/**
	 * Method for executing the checker evaluator from another class
	 * 
	 * @param args    - Arguments for the evaluator
	 * @param args[0] - Report file in JSON format
	 * @param args[1] - File to export to
	 */
	public static void evaluate(String args[]) {
		if (args.length >= 2) {
			File report = new File(args[0]);
			File output = new File(args[1]);
			InvokerUtils.outputResultsToFile(evaluateCheckers(InvokerUtils.deserialiseReports(report)), output);
		}
	}

}

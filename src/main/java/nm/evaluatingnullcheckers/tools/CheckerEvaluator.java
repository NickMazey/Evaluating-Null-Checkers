package nm.evaluatingnullcheckers.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.tools.CheckerOutputParser.CheckerOutput;
import nm.evaluatingnullcheckers.tools.CheckerOutputParser.KnownChecker;

/**
 * Evaluator for the null checkers Reads output from the parser and creates
 * results
 * 
 * @author Nick Mazey
 *
 */
public class CheckerEvaluator {

	public enum Flag {
		TRUEPOSITIVE, FALSEPOSITIVE, TRUENEGATIVE, FALSENEGATIVE, ERROR;
	}

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
	 * Gets all metadata for specified benchmarks
	 * 
	 * @param names - The simple class names to search for
	 * @return - Map from class name to annotations
	 */
	public static HashMap<String, ArrayList<Annotation>> getMetadata(ArrayList<String> names) {
		HashMap<String, ArrayList<Annotation>> metadata = new HashMap<String, ArrayList<Annotation>>();
		if (names != null) {
			HashSet<Class<?>> benchmarkClasses = BenchmarkSpace.getAllBenchmarkClasses();
			for (Class<?> clazz : benchmarkClasses) {
				String simpleName = clazz.getSimpleName();
				if (names.contains(simpleName)) {
					ArrayList<Annotation> annotations = new ArrayList<Annotation>();
					for (Annotation annotation : clazz.getAnnotations()) {
						annotations.add(annotation);
					}
					metadata.put(simpleName, annotations);
				}
			}
		}
		return metadata;
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
							if (annotation.annotationType().equals(BenchmarkAnnotations.ExpectedTrue.class)) {
								expectedOutputs.put(subjectName, CheckerOutput.VULNERABLE);
							} else if (annotation.annotationType().equals(BenchmarkAnnotations.ExpectedFalse.class)) {
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
		HashMap<String, ArrayList<Annotation>> metadata = getMetadata(names);
		HashMap<String, CheckerOutput> expectedOutputs = getExpectedOutput(metadata);
		HashMap<KnownChecker, CheckerResult> results = new HashMap<KnownChecker, CheckerResult>();
		if (output != null) {
			for (KnownChecker checker : output.keySet()) {
				HashMap<String, Flag> subjectResults = new HashMap<String, Flag>();
				HashMap<String, String> subjectMessages = new HashMap<String, String>();
				for (CheckerReport report : output.get(checker)) {
					String subjectName = report.getSubjectName();
					CheckerOutput expectedOutput = expectedOutputs.get(subjectName);
					CheckerOutput reportOutput = report.getOutput();
					subjectResults.put(subjectName, getFlag(expectedOutput, reportOutput));
					subjectMessages.put(subjectName, report.getMessage());
				}
				double precision = calculatePrecision(subjectResults.values());
				double recall = calculateRecall(subjectResults.values());
				results.put(checker, new CheckerResult(precision, recall, 0, subjectResults, subjectMessages));
			}

		}
		return results;
	}

	/**
	 * Writes results to a file in JSON format
	 * 
	 * @param results - Results map to write
	 * @param file    - File for writing
	 */
	public static void outputResultsToFile(HashMap<KnownChecker, CheckerResult> results, File file) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String output = mapper.writeValueAsString(results);
			FileWriter writer = new FileWriter(file);
			writer.write(output);
			writer.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for de-serialising results from checker evaluator
	 * 
	 * @param file - JSON file to read from
	 * @return - The results de-serialised
	 */
	public static HashMap<KnownChecker, CheckerResult> deserialiseResults(File file) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<KnownChecker, CheckerResult>> outputRef = new TypeReference<HashMap<KnownChecker, CheckerResult>>() {
		};
		try {
			HashMap<KnownChecker, CheckerResult> output = mapper.readValue(file, outputRef);
			return output;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<KnownChecker, CheckerResult>();
	}

	/**
	 * Method for executing the checker evaluator
	 * 
	 * @param args    - Arguments for the evaluator
	 * @param args[0] - Report file in JSON format
	 * @param args[1] - File to export to
	 */
	public static void main(String args[]) {
		if (args.length >= 2) {
			File report = new File(args[0]);
			File output = new File(args[1]);
			outputResultsToFile(evaluateCheckers(CheckerOutputParser.deserialiseReports(report)), output);
			deserialiseResults(output);
		}
	}

}

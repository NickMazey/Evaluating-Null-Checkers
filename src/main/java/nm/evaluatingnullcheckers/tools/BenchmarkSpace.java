package nm.evaluatingnullcheckers.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import nm.evaluatingnullcheckers.benchmarks.*;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;

public class BenchmarkSpace {
	
	private ArrayList<Class<?>> annotationTypes;
	private HashMap<Class<?>,ArrayList<Class<?>>> annotationSubTypes;
	
	private PrintStream outputStream;
	
	public BenchmarkSpace() {
		this(System.out);
	}
	
	public BenchmarkSpace(PrintStream outStream) {
		loadAnnotationTypes();
		this.outputStream = outStream;
	}
	
	private void loadAnnotationTypes() {
		annotationTypes = new ArrayList<Class<?>>();
		annotationTypes.add(BenchmarkAnnotations.Annotation.class);
		annotationTypes.add(BenchmarkAnnotations.AnalysisScope.class);
		annotationTypes.add(BenchmarkAnnotations.VariableScope.class);
		annotationTypes.add(BenchmarkAnnotations.VariableType.class);
		annotationTypes.add(BenchmarkAnnotations.ExpectedNPE.class);
		loadAnnotationSubTypes();
	}
	
	private void loadAnnotationSubTypes() {
		annotationSubTypes = new HashMap<Class<?>,ArrayList<Class<?>>>();
		for (Class<?> clazz : annotationTypes) {
			annotationSubTypes.put(clazz, new ArrayList<Class<?>>());
		}
		
		Class<?>[] classList = BenchmarkAnnotations.class.getClasses();
		for(Class<?> clazz : classList) {
			Annotation[] clazzAnnotations = clazz.getAnnotations();
			for (Annotation annotation : clazzAnnotations) {
				if(annotationTypes.contains((annotation.annotationType()))){
					ArrayList<Class<?>> subTypes = annotationSubTypes.get(annotation.annotationType());
					subTypes.add(clazz);
					annotationSubTypes.put(annotation.getClass(),subTypes);
				}
			}
		}
	}
	
	public void outputMissingBenchmarksToCSV() throws Exception{
		outputMissingBenchmarksToCSV("missing.csv");
	}
	
	public void outputMissingBenchmarksToCSV(String fileName) throws Exception{
		outputMissingBenchmarksToCSV(new File(fileName));
	}
	
	public void outputMissingBenchmarksToCSV(File file) throws Exception {
		HashSet<Class<?>> benchmarks = getAllBenchmarkClasses();
        HashSet<String> annotationCombinations = getPresentAnnotationCombinations(benchmarks);
        HashSet<String> missingCombinations = this.findMissingCombinations(annotationCombinations);
        String toWrite = this.combinationSetToCSV(missingCombinations);
		FileWriter writer = new FileWriter(file);
		writer.write(toWrite);
		writer.flush();
		writer.close();
		outputStream.println("missing combinations written to " + file.getAbsolutePath());
		
	}
	
	public void printBenchmarkInfo() {
		//Count how many combinations there are
		int combinations = 1;
		for(Class<?> clazz : annotationTypes) {
			int number = annotationSubTypes.get(clazz).size();
			combinations *= number;
			outputStream.println("For annotation type " + clazz.getSimpleName() + " there are " + number + " subtypes" );
		}
		HashSet<Class<?>> benchmarks = getAllBenchmarkClasses();
        HashSet<String> annotationCombinations = getPresentAnnotationCombinations(benchmarks);
        outputStream.println("Of " + combinations + " possible unique annotation combinations, " + annotationCombinations.size() + " could be found");            
	}
	
	private HashSet<String> findMissingCombinations(HashSet<String> foundCombinations){
		HashSet<String> allCombinations = getAllPossibleAnnotationCombinations();
		for (String combination : foundCombinations) {
			allCombinations.remove(combination);
		}
		return allCombinations;
	}
	
	private String combinationSetToPrettyString(HashSet<String> combinations) {
		StringBuilder prettyCombinationSet = new StringBuilder();
		for(String combination : combinations) {
			prettyCombinationSet.append(combinationToPrettyString(combination));
			prettyCombinationSet.append("\n");
		}
		return prettyCombinationSet.toString();
	}
	
	private String combinationSetToCSV(HashSet<String> combinations) {
		StringBuilder combinationCSV = new StringBuilder();
        // Column titles
        for(int i = 0; i < annotationTypes.size(); i++) {
        	if(i != 0) {
        		combinationCSV.append(",");
        	}
        	combinationCSV.append(annotationTypes.get(i).getSimpleName());
        }
        combinationCSV.append("\n");
        
        //Content
        for(String combination : combinations) {
        	combinationCSV.append(combinationToCSVLine(combination));
        	combinationCSV.append("\n");
        }
		return combinationCSV.toString();
	}
	
	private String combinationToCSVLine(String combination) {
		return combinationToCSVLine(combinationToArray(combination));
	}
	
	private String combinationToCSVLine(ArrayList<String> combination) {
		StringBuilder CSVLine = new StringBuilder();
		for(int i = 0; i < combination.size(); i++) {
			if(i != 0) {
				CSVLine.append(",");
			}
			CSVLine.append(combination.get(i));
		}
		return CSVLine.toString();
	}
	
	private String combinationToPrettyString(ArrayList<String> combination) {
		StringBuilder prettyCombination = new StringBuilder();
		for(int i = 0; i < combination.size(); i++) {
			if (i != 0) {
				for (int j = 0; j < i; j ++) {
					prettyCombination.append('\t');
				}
				prettyCombination.append('-');
			}
			
			prettyCombination.append(combination.get(i));
			if(i != combination.size() -1) {
				prettyCombination.append("\n");
			}
		}
		return prettyCombination.toString();
	}
	
	private String combinationToPrettyString(String combination) {
		return combinationToPrettyString(combinationToArray(combination));
	}
	
	private ArrayList<String> combinationToArray(String combination){
		char[] values = combination.toCharArray();
		String[] annotations = new String[values.length];
		for(int i = 0; i < values.length; i++) {
			Class<?> annotationType = annotationTypes.get(i);
			int subTypeIndex = Integer.valueOf(values[i]);
			String annotationName = annotationSubTypes.get(annotationType).get(subTypeIndex).getSimpleName();
			annotations[i] = annotationName;
		}
		return new ArrayList<String>(Arrays.asList(annotations));
	}
	
	public static HashSet<Class<?>> getAllBenchmarkClasses(){
		HashSet<Class<?>> benchmarks = new HashSet<Class<?>>();
		String packageName = "nm.evaluatingnullcheckers.benchmarks";
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String str = null;
        try {
        for(str = reader.readLine(); str !=null;str = reader.readLine()) {
        	benchmarks.add(Class.forName(packageName + "." + str.substring(0,str.lastIndexOf("."))));
        }
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return benchmarks;
	}
	
	private HashSet<String> getPresentAnnotationCombinations(HashSet<Class<?>> benchmarks){
        //Check how many unique combinations have been done
        HashSet<String> annotationCombinations = new HashSet<String>();
        for(Class<?> benchmark : benchmarks) {
        	char[] combination = new char[annotationTypes.size()];
        	for(Annotation annotation : benchmark.getAnnotations()) {
        		Class<?> annotationType = annotation.annotationType();
        		for(Class<?> anno : annotationTypes) {
        			int annotationSuperTypeIndex = annotationTypes.indexOf(anno);
        			ArrayList<Class<?>> subTypes = annotationSubTypes.get(anno);
        			if(subTypes.contains(annotationType)) {
        				int annotationSubTypeIndex = subTypes.indexOf(annotationType);
        				combination[annotationSuperTypeIndex] = (char) annotationSubTypeIndex;
        			}
        		}
        	}
        	String combinationStr = new String(combination);
        	annotationCombinations.add(combinationStr);
        	
        }
        return annotationCombinations;
	}
	
	private HashSet<String> getAllPossibleAnnotationCombinations(){
		int finalIndex = annotationTypes.size();
		int currentIndex = 0;
		char[] start = new char[1];
		HashSet<String> currentPerms = new HashSet<String>();
		while(currentIndex < finalIndex) {
			int size = annotationSubTypes.get(annotationTypes.get(currentIndex)).size();
			HashSet<String> newPerms = new HashSet<String>();
			if(currentIndex == 0) {
				newPerms = getAllValuesForIndex(new String(start), currentIndex, size);
			} else {
				for(String existingPerm : currentPerms) {
					String base = existingPerm + ((char) 0);
					HashSet<String> allValues = getAllValuesForIndex(base,currentIndex,size);
					for(String value : allValues) {
						newPerms.add(value);
					}
				}
			}
			currentIndex++;
			currentPerms = newPerms;
		}
		return currentPerms;
	}
		
	private HashSet<String> getAllValuesForIndex(String base, int index, int numValues){
		HashSet<String> combinations = new HashSet<String>();
		assert(index < base.length());
		for(int i = 0; i < numValues; i++) {
			char[] newStr = base.toCharArray();
			newStr[index] = (char) i;
			combinations.add(new String(newStr));
		}
		return combinations;
	}
	
	
	public static void main(String[] args) {
		BenchmarkSpace bench = new BenchmarkSpace();
		bench.printBenchmarkInfo();
		try{
			bench.outputMissingBenchmarksToCSV();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

}

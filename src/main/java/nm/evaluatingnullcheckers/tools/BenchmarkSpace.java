package nm.evaluatingnullcheckers.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import nm.evaluatingnullcheckers.benchmarks.*;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;

public class BenchmarkSpace {
	
	private ArrayList<Class<?>> annotationTypes;
	private HashMap<Class<?>,ArrayList<Class<?>>> annotationSubTypes;
	
	public BenchmarkSpace() {
		annotationTypes = new ArrayList<Class<?>>();
		annotationTypes.add(BenchmarkAnnotations.Annotation.class);
		annotationTypes.add(BenchmarkAnnotations.AnalysisScope.class);
		annotationTypes.add(BenchmarkAnnotations.VariableScope.class);
		annotationTypes.add(BenchmarkAnnotations.VariableType.class);
		annotationTypes.add(BenchmarkAnnotations.ExpectedNPE.class);

		
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
	
	public void visualiseBenchmarks() {
		//Count how many combinations there are
		int combinations = 1;
		for(Class<?> clazz : annotationTypes) {
			int number = annotationSubTypes.get(clazz).size();
			combinations *= number;
			System.out.println("For annotation type " + clazz.getSimpleName() + " there are " + number + " subtypes" );
		}
		System.out.println("There are a total of " + combinations + " possible combinations");
		
		HashSet<Class<?>> benchmarks = getAllBenchmarkClasses();

        
        HashSet<String> annotationCombinations = getPresentAnnotationCombinations(benchmarks);
        
        
        System.out.println("Of " + combinations + " possible annotation combinations, " + annotationCombinations.size() + " could be found");
        
        
        HashSet<String> missingCombinations = getAllPossibleAnnotationCombinations();
        for(String combination : annotationCombinations) {
        	missingCombinations.remove(combination);
        }
        
        for(String str : missingCombinations) {
        	
        }
        
        
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
	
	private HashSet<Class<?>> getAllBenchmarkClasses(){
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
		bench.visualiseBenchmarks();
	}

}

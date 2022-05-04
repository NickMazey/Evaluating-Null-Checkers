package nm.evaluatingnullcheckers.tools;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import nm.evaluatingnullcheckers.benchmarks.*;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;

public class BenchmarkSpace {
	
	private HashSet<Class<?>> annotationTypes;
	private HashMap<Class<?>,HashSet<Class<?>>> annotationSubTypes;
	
	public BenchmarkSpace() {
		annotationTypes = new HashSet<Class<?>>();
		annotationTypes.add(BenchmarkAnnotations.ExpectedNPE.class);
		annotationTypes.add(BenchmarkAnnotations.VariableType.class);
		annotationTypes.add(BenchmarkAnnotations.VariableScope.class);
		annotationTypes.add(BenchmarkAnnotations.AnalysisScope.class);
		annotationTypes.add(BenchmarkAnnotations.Annotation.class);
		
		annotationSubTypes = new HashMap<Class<?>,HashSet<Class<?>>>();
		for (Class<?> clazz : annotationTypes) {
			annotationSubTypes.put(clazz, new HashSet<Class<?>>());
		}
		
		Class<?>[] classList = BenchmarkAnnotations.class.getClasses();
		for(Class<?> clazz : classList) {
			Annotation[] clazzAnnotations = clazz.getAnnotations();
			for (Annotation annotation : clazzAnnotations) {
				if(annotationTypes.contains((annotation.annotationType()))){
					HashSet<Class<?>> subTypes = annotationSubTypes.get(annotation.annotationType());
					subTypes.add(clazz);
					annotationSubTypes.put(annotation.getClass(),subTypes);
				}
			}
		}
		
		
	}
	
	public void VisualiseBenchmarks() {
		for(Class<?> clazz : annotationTypes) {
			int number = annotationSubTypes.get(clazz).size();
			System.out.println("For annotation type " + clazz.getSimpleName() + " there are " + number + " subtypes" );
		}
	}
	
	public static void main(String[] args) {
		BenchmarkSpace bench = new BenchmarkSpace();
		bench.VisualiseBenchmarks();
	}

}

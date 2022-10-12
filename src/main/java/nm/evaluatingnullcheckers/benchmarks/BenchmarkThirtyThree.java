package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Generic;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * Non-annotated version of benchmarknine
 * 
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Nonannotated
@Intraprocedural
@Field
@Generic
@NPEProne
@Imperative
public class BenchmarkThirtyThree <T> {
	
	T object = null;
	
	public BenchmarkThirtyThree() {}

	public void throwNPE() {
		object.toString();
	}

}

package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Generic;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * Non-annotated version of benchmarktwentyone
 * 
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Nonannotated
@Intraprocedural
@Field
@Generic
@NPEProof
@Imperative
public class BenchmarkFourtyFive <T> {
	
	T object = null;
	
	public BenchmarkFourtyFive() {}

	public void throwNPE() {
		if(object != null) {
			object.toString();
		}
	}

}

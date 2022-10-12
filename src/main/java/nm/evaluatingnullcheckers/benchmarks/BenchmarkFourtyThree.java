package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Generic;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * None-annotated version of benchmarknineteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Parameter
@Generic
@NPEProof
@Imperative
public class BenchmarkFourtyThree {

	public static <T> void throwNPE(T object){
		if(object != null) {
			object.toString();
		}
	}

}

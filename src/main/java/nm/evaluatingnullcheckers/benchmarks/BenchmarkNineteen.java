package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Generic;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Null-guarded version of benchmarkseven
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@Generic
@NPEProof
@Imperative
public class BenchmarkNineteen {

	public static <T> void throwNPE(@Nullable T object){
		if(object != null) {
			object.toString();
		}
	}

}

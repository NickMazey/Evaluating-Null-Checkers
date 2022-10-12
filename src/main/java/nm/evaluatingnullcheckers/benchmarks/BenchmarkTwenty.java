package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Generic;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Null-guarded version of benchmarkeight
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@Generic
@NPEProof
@Imperative
public class BenchmarkTwenty {
	public static <T> void throwNPE(){
		@Nullable T object = null;
		if(object != null) {
		object.toString();
		}
	}
}

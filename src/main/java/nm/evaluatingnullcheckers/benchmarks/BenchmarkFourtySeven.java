package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Array;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarktwentythree
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@Array
@NPEProof
@Imperative
public class BenchmarkFourtySeven {
	
	private static Object[] getArray() {
		return null;
	}
	
	public static void throwNPE() {
		if(getArray() != null && getArray().length > 0) {
			Object obj = getArray()[0];
		}
	}
}

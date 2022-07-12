package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;

/**
 * Non-annotated version of benchmarktwentythree
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@ReturnSource
@ArrayNPE
@NPEProof
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

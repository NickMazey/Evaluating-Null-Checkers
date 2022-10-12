package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarktwentyfour
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@BenchmarkAnnotations.GenericVar
@NPEProof
@Imperative
public class BenchmarkFourtyEight {
	private static <T> T getGeneric() {
		return null;
	}
	
	public static <T> void throwNPE() {
		if(getGeneric() != null) {
			getGeneric().toString();
		}
	}
}

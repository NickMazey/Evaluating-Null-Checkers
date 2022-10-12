package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkfourteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Local
@ArrayNPE
@NPEProof
@Imperative
public class BenchmarkThirtyEight {

	public static void throwNPE() {
		int[] numbers = null;
		if(numbers != null) {
			int i = numbers[0];
		}
	}
}

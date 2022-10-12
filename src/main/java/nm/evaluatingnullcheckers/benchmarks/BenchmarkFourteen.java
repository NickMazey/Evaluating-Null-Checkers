package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;

/**
 * Null-guarded version of benchmarktwo
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@ArrayNPE
@NPEProof
@Imperative
public class BenchmarkFourteen {

	public static void throwNPE() {
		@Nullable
		int[] numbers = null;
		if(numbers != null) {
			int i = numbers[0];
		}
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedFalse;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;

/**
 * Null-guarded version of benchmarktwo
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ArrayNPE
@ExpectedFalse
public class BenchmarkFourteen {

	public static int throwNPE() {
		@Nullable
		int[] numbers = null;
		if(numbers != null) {
			return numbers[0];
		}
		return 0;
	}
}

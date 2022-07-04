package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedFalse;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;

/**
 * Null-guarded version of benchmarkfour
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@FieldSource
@ArrayNPE
@ExpectedFalse
public class BenchmarkSixteen {
	@Nullable
	int[] numbers = null;
	
	public BenchmarkSixteen() {}
	
	public int throwNPE() {
		if(numbers != null) {
			return numbers[0];
		}
		return 0;
	}
}

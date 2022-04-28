package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@FieldSource
@ArrayNPE
@ExpectedTrue
public class BenchmarkFour {
	@Nullable
	int[] numbers = null;
	
	public BenchmarkFour() {}
	
	public int throwNPE() {
		return numbers[0];
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedFalse;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;

/**
 * Null-guarded version of benchmarknine
 * 
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Annotated
@Intraprocedural
@FieldSource
@GenericsNPE
@ExpectedFalse
public class BenchmarkTwentyOne <T> {
	
	@Nullable T object = null;
	
	public BenchmarkTwentyOne() {}

	public @Nullable String throwNPE() {
		if(object != null) {
		return object.toString();
		}
		return null;
	}

}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;

/**
 * 
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Annotated
@Intraprocedural
@FieldSource
@GenericsNPE
@ExpectedTrue
public class BenchmarkNine <T> {
	
	@Nullable T object = null;
	
	public BenchmarkNine() {}

	public String throwNPE() {
		return object.toString();
	}

}

package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarknine
 * 
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Nonannotated
@Intraprocedural
@FieldSource
@GenericsNPE
@ExpectedTrue
public class BenchmarkThirtyThree <T> {
	
	T object = null;
	
	public BenchmarkThirtyThree() {}

	public String throwNPE() {
		return object.toString();
	}

}

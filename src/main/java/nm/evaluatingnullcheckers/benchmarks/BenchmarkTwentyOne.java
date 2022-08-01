package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
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
@NPEProof
public class BenchmarkTwentyOne <T> {
	
	@Nullable T object = null;
	
	public BenchmarkTwentyOne() {}

	public void throwNPE() {
		if(object != null) {
		object.toString();
		}
	}

}

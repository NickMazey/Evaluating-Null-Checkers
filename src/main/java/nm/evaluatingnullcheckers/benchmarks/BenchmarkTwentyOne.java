package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * Null-guarded version of benchmarknine
 * 
 * @author Nick Mazey
 *
 * @param <T> - GenericVar type
 */
@Annotated
@Intraprocedural
@Field
@GenericVar
@NPEProof
@Imperative
public class BenchmarkTwentyOne <T> {
	
	@Nullable T object = null;
	
	public BenchmarkTwentyOne() {}

	public void throwNPE() {
		if(object != null) {
		object.toString();
		}
	}

}

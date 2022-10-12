package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Null-guarded version of LambdaBenchmarkEight
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Annotated
@Intraprocedural
@Field
@Generic
@NPEProof
@Lambda
public class LambdaBenchmarkNineteen<T> {

	@Nullable T object = null;

	public LambdaBenchmarkNineteen() {}

	public Runnable throwNPE = ()-> {
		if(object != null) {
			object.toString();
		}
	};

}

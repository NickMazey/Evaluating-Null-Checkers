package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Lambda version of BenchmarkNine
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Annotated
@Intraprocedural
@Field
@GenericsNPE
@NPEProne
@Lambda
public class LambdaBenchmarkEight<T> {

	@Nullable T object = null;

	public LambdaBenchmarkEight() {}

	public Runnable throwNPE = ()-> {
		object.toString();
	};

}

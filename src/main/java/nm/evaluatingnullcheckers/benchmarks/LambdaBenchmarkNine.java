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
@FieldSource
@GenericsNPE
@NPEProne
public class LambdaBenchmarkNine<T> {

	@Nullable T object = null;

	public LambdaBenchmarkNine() {}

	public Runnable throwNPE = ()-> {
		object.toString();
	};

}

package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Lambda version of BenchmarkThree
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@FieldSource
@ObjectNPE
@NPEProne
public class LambdaBenchmarkThree {
	@Nullable
	Object obj = null;

	public LambdaBenchmarkThree() {}
	
	public Runnable throwNPE = () -> {
		obj.toString();
	};

}

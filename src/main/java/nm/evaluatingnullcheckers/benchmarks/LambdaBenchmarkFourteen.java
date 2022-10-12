package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Null-guarded version of LambdaBenchmarkThree
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Field
@ObjectNPE
@NPEProof
@Lambda
public class LambdaBenchmarkFourteen {
	@Nullable
	Object obj = null;

	public LambdaBenchmarkFourteen() {}
	
	public Runnable throwNPE = () -> {
		if(obj != null) {
			obj.toString();
		}
	};

}

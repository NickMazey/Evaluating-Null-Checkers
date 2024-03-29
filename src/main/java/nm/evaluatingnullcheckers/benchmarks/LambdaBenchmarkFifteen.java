package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Null-guarded version of LambdaBenchmarkFour
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Field
@ArrayVar
@NPEProof
@Lambda
public class LambdaBenchmarkFifteen {
	@Nullable
	int[] numbers = null;

	public LambdaBenchmarkFifteen() {}
	
	public Runnable throwNPE= ()->{
		if(numbers != null && numbers.length > 0) {
			int i = numbers[0];
		}
	};
}

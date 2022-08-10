package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Lambda version of BenchmarkFour
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@FieldSource
@ArrayNPE
@NPEProne
public class LambdaBenchmarkFour {
	@Nullable
	int[] numbers = null;

	public LambdaBenchmarkFour() {}
	
	public Runnable throwNPE= ()->{
		int i = numbers[0];
	};
}

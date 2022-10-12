package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.lang.Object;

/**
 * Lambda version of BenchmarkThree
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Field
@ObjectVar
@NPEProne
@Lambda
public class LambdaBenchmarkThree {
	@Nullable
    Object obj = null;

	public LambdaBenchmarkThree() {}
	
	public Runnable throwNPE = () -> {
		obj.toString();
	};

}

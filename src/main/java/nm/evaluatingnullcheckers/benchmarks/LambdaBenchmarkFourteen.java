package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.lang.Object;

/**
 * Null-guarded version of LambdaBenchmarkThree
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Field
@BenchmarkAnnotations.Object
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

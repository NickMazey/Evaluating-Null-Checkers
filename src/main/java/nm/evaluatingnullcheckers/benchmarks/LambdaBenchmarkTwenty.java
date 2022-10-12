package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.lang.Object;
import java.util.function.Supplier;

/**
 * Null-guarded version of LambdaBenchmarkNine
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@ObjectVar
@NPEProof
@Lambda
public class LambdaBenchmarkTwenty {
	
	private static Supplier<Object> getObject = ()->null;
	
	public static Runnable throwNPE = ()->{
		if(getObject.get() != null) {
			getObject.get().toString();
		}
	};
}

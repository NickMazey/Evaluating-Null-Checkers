package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.util.function.Supplier;

/**
 * Null-guarded version of LambdaBenchmarkNine
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@ReturnSource
@ObjectNPE
@NPEProof
@Lambda
public class LambdaBenchmarkTwenty {
	
	private static Supplier<Object> getObject = ()->null;
	
	public static Runnable throwNPE = ()->{
		Object o = getObject.get();
		if(o != null) {
			o.toString();
		}
	};
}

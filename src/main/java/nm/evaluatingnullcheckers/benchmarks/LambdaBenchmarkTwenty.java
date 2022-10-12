package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.lang.Object;
import java.util.function.Supplier;

/**
 * Null-guarded version of LambdaBenchmarkNine
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@Return
@BenchmarkAnnotations.Object
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

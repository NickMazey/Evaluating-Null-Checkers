package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.util.function.Supplier;

/**
 * Null-guarded version of LambdaBenchmarkEleven
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@GenericVar
@NPEProof
@Lambda
public class LambdaBenchmarkTwentyTwo {
	private static Supplier<? extends Object> getGeneric = ()->null;

	public static Runnable throwNPE = ()->{
		Object o = getGeneric.get();
		if(o != null) {
			getGeneric.get().toString();
		}
	};
}

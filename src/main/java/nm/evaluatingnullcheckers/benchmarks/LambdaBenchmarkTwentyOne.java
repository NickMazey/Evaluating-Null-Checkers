package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.util.function.Supplier;

/**
 * Null-guarded version of LambdaBenchmarkTen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@ArrayNPE
@NPEProof
@Lambda
public class LambdaBenchmarkTwentyOne {
	
	private static Supplier<Object[]> getArray = ()->null;
	
	public static Runnable throwNPE = ()-> {
		Object[] arr = getArray.get();
		if(arr != null && arr.length > 0) {
			Object obj = getArray.get()[0];
		}
	};
}

package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.util.function.Supplier;

/**
 * Lambda version of BenchmarkThirtyFive
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@ReturnSource
@ArrayNPE
@NPEProne
public class LambdaBenchmarkTen {
	
	private static Supplier<Object[]> getArray = ()->null;
	
	public static Runnable throwNPE = ()-> {
		Object obj = getArray.get()[0];
	};
}

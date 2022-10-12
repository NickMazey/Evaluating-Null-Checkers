package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.lang.Object;
import java.util.function.Supplier;

/**
 * Lambda version of BenchmarkThirtyFour
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@BenchmarkAnnotations.Object
@NPEProne
@Lambda
public class LambdaBenchmarkNine {
	
	private static Supplier<Object> getObject = ()->null;
	
	public static Runnable throwNPE = ()->{
		getObject.get().toString();
	};
}

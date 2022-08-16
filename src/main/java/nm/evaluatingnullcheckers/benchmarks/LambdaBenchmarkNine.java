package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.util.function.Supplier;

/**
 * Lambda version of BenchmarkThirtyFour
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@ReturnSource
@ObjectNPE
@NPEProne
@Lambda
public class LambdaBenchmarkNine {
	
	private static Supplier<Object> getObject = ()->null;
	
	public static Runnable throwNPE = ()->{
		getObject.get().toString();
	};
}

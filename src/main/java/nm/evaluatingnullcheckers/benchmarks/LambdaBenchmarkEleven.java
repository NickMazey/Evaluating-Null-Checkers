package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import java.util.function.Supplier;

/**
 * Lambda version of BenchmarkThirtySix
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@GenericsNPE
@NPEProne
@Lambda
public class LambdaBenchmarkEleven {
	private static Supplier<? extends Object> getGeneric = ()->null;

	public static Runnable throwNPE = ()->getGeneric.get().toString();
}

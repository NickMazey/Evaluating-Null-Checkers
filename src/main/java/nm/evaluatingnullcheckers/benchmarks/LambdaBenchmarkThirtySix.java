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
@ReturnSource
@GenericsNPE
@NPEProne
public class LambdaBenchmarkThirtySix {
	private static Supplier<? extends Object> getGeneric = ()->null;

	public static Runnable throwNPE = ()->getGeneric.get().toString();
}

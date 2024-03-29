package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkeleven
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@ArrayVar
@NPEProne
@Imperative
public class BenchmarkThirtyFive {
	
	private static Object[] getArray() {
		return null;
	}
	
	public static void throwNPE() {
		Object obj = getArray()[0];
	}
}

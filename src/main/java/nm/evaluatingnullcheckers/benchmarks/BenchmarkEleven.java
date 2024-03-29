package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@Return
@ArrayVar
@NPEProne
@Imperative
public class BenchmarkEleven {
	
	private static @Nullable Object[] getArray() {
		return null;
	}
	
	public static void throwNPE() {
		Object obj = getArray()[0];
	}
}

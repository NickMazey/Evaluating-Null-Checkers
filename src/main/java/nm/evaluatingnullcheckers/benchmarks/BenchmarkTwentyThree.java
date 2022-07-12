package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedFalse;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;

/**
 * Null-guarded version of benchmarkeleven
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@ReturnSource
@ArrayNPE
@ExpectedFalse
public class BenchmarkTwentyThree {
	
	private static @Nullable Object[] getArray() {
		return null;
	}
	
	public static void throwNPE() {
		if(getArray() != null && getArray().length > 0) {
			Object obj = getArray()[0];
		}
	}
}

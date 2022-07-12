package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedFalse;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;

/**
 * Null-guarded version of benchmarktwelve
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@ReturnSource
@GenericsNPE
@ExpectedFalse
public class BenchmarkTwentyFour {
	private static <T> @Nullable T getGeneric() {
		return null;
	}
	
	public static <T> void throwNPE() {
		if(getGeneric() != null) {
			getGeneric().toString();
		}
	}
}

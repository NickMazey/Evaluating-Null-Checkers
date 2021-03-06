package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@ReturnSource
@GenericsNPE
@NPEProne
public class BenchmarkTwelve {
	private static <T> @Nullable T getGeneric() {
		return null;
	}
	
	public static <T> void throwNPE() {
		getGeneric().toString();
	}
}

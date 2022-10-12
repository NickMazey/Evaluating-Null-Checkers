package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Generic;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@Return
@Generic
@NPEProne
@Imperative
public class BenchmarkTwelve {
	private static <T> @Nullable T getGeneric() {
		return null;
	}
	
	public static <T> void throwNPE() {
		getGeneric().toString();
	}
}

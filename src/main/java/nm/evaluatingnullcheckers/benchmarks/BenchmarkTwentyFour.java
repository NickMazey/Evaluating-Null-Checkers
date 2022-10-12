package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Null-guarded version of benchmarktwelve
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@Return
@GenericsNPE
@NPEProof
@Imperative
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

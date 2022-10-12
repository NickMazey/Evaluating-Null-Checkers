package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * Null-guarded version of benchmarkone
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@BenchmarkAnnotations.Object
@NPEProof
@Imperative
public class BenchmarkThirteen {

	public static void throwNPE() {
		@Nullable
		String str = null;
		if(str != null) {
			str.length();
		}
	}
}

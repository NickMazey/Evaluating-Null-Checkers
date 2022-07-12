package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * Null-guarded version of benchmarkone
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ObjectNPE
@NPEProof
public class BenchmarkThirteen {

	public static int throwNPE() {
		@Nullable
		String str = null;
		if(str != null) {
			return str.length();
		}
		return 0;
	}
}

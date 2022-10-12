package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Object;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * Null-guarded version of benchmarkfive
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@Object
@NPEProof
@Imperative
public class BenchmarkSeventeen {
	public static void StringLength(@Nullable String str) {
		if(str != null) {
		int i = str.length();
		}
	}
}

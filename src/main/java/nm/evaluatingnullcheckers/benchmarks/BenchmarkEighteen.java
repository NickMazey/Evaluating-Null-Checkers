package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;

/**
 * Null-guarded version of benchmarksix
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@ArrayNPE
@NPEProof
@Imperative
public class BenchmarkEighteen {
	public static void FirstIndex(@Nullable Object[] arr) {
		if(arr != null && arr.length != 0) {
			@Nullable Object o = arr[0];
		}
	}
}

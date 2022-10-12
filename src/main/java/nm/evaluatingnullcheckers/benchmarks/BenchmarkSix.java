package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@ArrayNPE
@NPEProne
@Imperative
public class BenchmarkSix {
	public static void FirstIndex(@Nullable Object[] arr) {
		Object o = arr[0];
	}
}

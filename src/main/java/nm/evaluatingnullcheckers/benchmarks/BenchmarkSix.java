package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@MethodParameterSource
@ArrayNPE
@NPEProne
public class BenchmarkSix {
	public static void FirstIndex(@Nullable Object[] arr) {
		Object o = arr[0];
	}
}

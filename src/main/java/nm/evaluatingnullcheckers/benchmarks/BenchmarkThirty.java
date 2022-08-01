package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarksix
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@MethodParameterSource
@ArrayNPE
@NPEProne
public class BenchmarkThirty {
	public static void FirstIndex(Object[] arr) {
		Object o = arr[0];
	}
}

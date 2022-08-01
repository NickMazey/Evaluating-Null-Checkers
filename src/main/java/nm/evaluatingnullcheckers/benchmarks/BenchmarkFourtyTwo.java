package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarkeighteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@MethodParameterSource
@ArrayNPE
@NPEProof
public class BenchmarkFourtyTwo {
	public static void FirstIndex(Object[] arr) {
		if(arr != null && arr.length != 0) {
			Object o = arr[0];
		}
	}
}

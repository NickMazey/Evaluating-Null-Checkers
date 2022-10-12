package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * Non-annotated version of benchmarkeighteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Parameter
@ArrayNPE
@NPEProof
@Imperative
public class BenchmarkFourtyTwo {
	public static void FirstIndex(Object[] arr) {
		if(arr != null && arr.length != 0) {
			Object o = arr[0];
		}
	}
}

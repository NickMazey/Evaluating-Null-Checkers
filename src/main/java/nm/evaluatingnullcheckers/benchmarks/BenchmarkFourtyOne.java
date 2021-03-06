package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;

/**
 * Non-annotated version of benchmarkseventeen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@MethodParameterSource
@ObjectNPE
@NPEProof
public class BenchmarkFourtyOne {
	public static int StringLength(String str) {
		if(str != null) {
		return str.length();
		}
		return 0;
	}
}

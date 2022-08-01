package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;

/**
 * Non-annotated version of benchmarkfive
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@MethodParameterSource
@ObjectNPE
@NPEProne
public class BenchmarkTwentyNine {
	public static void StringLength(String str) {
		int i = str.length();
	}
}

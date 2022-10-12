package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkfive
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Parameter
@ObjectNPE
@NPEProne
@Imperative
public class BenchmarkTwentyNine {
	public static void StringLength(String str) {
		int i = str.length();
	}
}

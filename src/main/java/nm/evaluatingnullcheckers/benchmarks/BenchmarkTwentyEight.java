package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarkfour
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@FieldSource
@ArrayNPE
@NPEProne
public class BenchmarkTwentyEight {
	int[] numbers = null;
	
	public BenchmarkTwentyEight() {}
	
	public void throwNPE() {
		int i = numbers[0];
	}
}

package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkfour
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Field
@ArrayVar
@NPEProne
@Imperative
public class BenchmarkTwentyEight {
	int[] numbers = null;
	
	public BenchmarkTwentyEight() {}
	
	public void throwNPE() {
		int i = numbers[0];
	}
}

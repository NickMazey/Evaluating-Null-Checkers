package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmark sixteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Field
@ArrayVar
@NPEProof
@Imperative
public class BenchmarkFourty {
	int[] numbers = null;
	
	public BenchmarkFourty() {}
	
	public void throwNPE() {
		if(numbers != null) {
			int i = numbers[0];
		}
	}
}

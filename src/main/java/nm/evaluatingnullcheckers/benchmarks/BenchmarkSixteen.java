package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Null-guarded version of benchmarkfour
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Field
@ArrayVar
@NPEProof
@Imperative
public class BenchmarkSixteen {
	@Nullable
	int[] numbers = null;
	
	public BenchmarkSixteen() {}
	
	public void throwNPE() {
		if(numbers != null) {
			int i = numbers[0];
		}
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;

/**
 * Null-guarded version of benchmarkfour
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@FieldSource
@ArrayNPE
@NPEProof
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

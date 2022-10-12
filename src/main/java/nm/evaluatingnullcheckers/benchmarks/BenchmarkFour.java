package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Field
@ArrayNPE
@NPEProne
@Imperative
public class BenchmarkFour {
	@Nullable
	int[] numbers = null;
	
	public BenchmarkFour() {}
	
	public void throwNPE() {
		int i = numbers[0];
	}
}

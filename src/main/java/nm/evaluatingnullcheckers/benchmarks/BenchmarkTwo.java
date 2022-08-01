package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ArrayNPE
@NPEProne
public class BenchmarkTwo {
	/**
	 * Method to throw an NPE
	 */
	public static void throwNPE() {
		@Nullable
		int[] numbers = null;
		int i = numbers[0];
	}
}

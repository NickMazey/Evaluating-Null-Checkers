package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Array;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@Array
@NPEProne
@Imperative
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

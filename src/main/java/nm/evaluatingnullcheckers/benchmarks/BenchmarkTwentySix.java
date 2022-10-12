package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Array;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarktwo
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Local
@Array
@NPEProne
@Imperative
public class BenchmarkTwentySix {
	/**
	 * Method to throw an NPE
	 * @return length of a null array
	 */
	public static void throwNPE() {
		int[] numbers = null;
		int i = numbers[0];
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarktwo
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@LocalSource
@ArrayNPE
@NPEProne
public class BenchmarkTwentySix {
	/**
	 * Method to throw an NPE
	 * @return length of a null array
	 */
	public static int throwNPE() {
		int[] numbers = null;
		return numbers[0];
	}
}

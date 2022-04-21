package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;

/**
 * Simple benchmark that will throw an NPE if the "throwNPE" method is called
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ArrayNPE
@ExpectedTrue
public class BenchmarkTwo {
	/**
	 * Method to throw an NPE
	 * @return length of a null array
	 */
	public static int throwNPE() {
		@Nullable
		int[] numbers = null;
		return numbers[0];
	}
}

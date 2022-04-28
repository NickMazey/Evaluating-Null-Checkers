package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ObjectNPE
@ExpectedTrue
public class BenchmarkOne {
	/**
	 * Method to throw an NPE
	 * @return length of a null string
	 */
	public static int throwNPE() {
		@Nullable
		String str = null;
		return str.length();
	}
}

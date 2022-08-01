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
@NPEProne
public class BenchmarkOne {
	/**
	 * Method to throw an NPE
	 */
	public static void throwNPE() {
		@Nullable
		String str = null;
		str.length();
	}
}

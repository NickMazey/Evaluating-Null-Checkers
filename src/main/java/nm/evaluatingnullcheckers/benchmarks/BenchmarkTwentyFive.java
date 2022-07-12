package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * Non-Annotated version of benchmarkone
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@LocalSource
@ObjectNPE
@NPEProne
public class BenchmarkTwentyFive {
	/**
	 * Method to throw an NPE
	 * @return length of a null string
	 */
	public static int throwNPE() {
		String str = null;
		return str.length();
	}
}

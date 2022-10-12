package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * Non-Annotated version of benchmarkone
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Local
@ObjectVar
@NPEProne
@Imperative
public class BenchmarkTwentyFive {

	public static void throwNPE() {
		String str = null;
		int i = str.length();
	}
}

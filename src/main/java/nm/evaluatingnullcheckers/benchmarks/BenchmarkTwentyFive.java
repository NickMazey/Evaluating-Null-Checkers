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
@Imperative
public class BenchmarkTwentyFive {

	public static void throwNPE() {
		String str = null;
		int i = str.length();
	}
}

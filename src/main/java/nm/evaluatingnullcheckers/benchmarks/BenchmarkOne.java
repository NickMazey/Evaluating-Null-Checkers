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
@Local
@ObjectVar
@NPEProne
@Imperative
public class BenchmarkOne {
	public static void throwNPE() {
		@Nullable
		String str = null;
		str.length();
	}
}

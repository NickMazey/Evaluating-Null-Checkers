package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Object;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Initial test to see how the checkers handle flaky semantics
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@Object
@NPEProne
@Imperative
public class FlakySemanticsBenchmarkOne {
	public static void throwNPE() {
		@Nullable java.lang.Object obj = new java.lang.Object();
		//Every other millisecond
		if(System.currentTimeMillis() % 2 == 1) {
			obj = null;
		}
		obj.toString();
	}
}

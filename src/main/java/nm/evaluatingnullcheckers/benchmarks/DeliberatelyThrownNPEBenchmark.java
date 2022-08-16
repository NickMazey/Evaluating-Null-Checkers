package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Basic test to observe how checkers handle explicitly thrown NPEs
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@LocalSource
@ObjectNPE
@NPEProne
@Imperative
public class DeliberatelyThrownNPEBenchmark {
	public static void throwNPE() {
		throw new NullPointerException();
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;

/**
 * Non-annotated version of benchmarktwelve
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@ReturnSource
@GenericsNPE
@NPEProne
public class BenchmarkThirtySix {
	private static <T> T getGeneric() {
		return null;
	}
	
	public static <T> void throwNPE() {
		getGeneric().toString();
	}
}

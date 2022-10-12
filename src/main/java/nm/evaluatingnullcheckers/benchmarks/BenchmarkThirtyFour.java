package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Object;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkten
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@Object
@NPEProne
@Imperative
public class BenchmarkThirtyFour {
	
	private static java.lang.Object getObject() {
		return null;
	}
	
	public static void throwNPE() {
		getObject().toString();
	}
}

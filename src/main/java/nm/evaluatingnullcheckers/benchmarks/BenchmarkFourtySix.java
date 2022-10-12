package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Object;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarktwentytwo
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Interprocedural
@Return
@Object
@NPEProof
@Imperative
public class BenchmarkFourtySix {
	
	private static java.lang.Object getObject() {
		return null;
	}
	
	public static void throwNPE() {
		if(getObject() != null) {
			getObject().toString();
		}
	}
}

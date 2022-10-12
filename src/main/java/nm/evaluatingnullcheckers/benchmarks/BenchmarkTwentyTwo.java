package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Null-guarded version of benchmarkten
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@Return
@ObjectVar
@NPEProof
@Imperative
public class BenchmarkTwentyTwo {
	
	private static @Nullable java.lang.Object getObject() {
		return null;
	}
	
	public static void throwNPE() {
		if(getObject() != null) {
			getObject().toString();
		}
	}
}

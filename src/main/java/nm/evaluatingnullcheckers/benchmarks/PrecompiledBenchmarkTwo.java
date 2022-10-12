package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Object;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.precompiledclasses.AnnotationConversions;

/**
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
public class PrecompiledBenchmarkTwo {
	public static void throwNPE() {
		AnnotationConversions.getNonNull().toString();
	}
}

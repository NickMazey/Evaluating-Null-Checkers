package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Object;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.precompiledclasses.AnnotationConversions;

@Annotated
@Interprocedural
@Return
@Object
@NPEProof
@Imperative
public class PrecompiledBenchmarkSix {
	public static void throwNPE() {
		AnnotationConversions.getNonNullIncorrectAnno().toString();
	}
}

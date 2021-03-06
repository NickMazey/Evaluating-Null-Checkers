package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;
import nm.precompiledclasses.AnnotationConversions;

@Annotated
@Interprocedural
@ReturnSource
@ObjectNPE
@NPEProof
public class PrecompiledBenchmarkFive {
	public static void throwNPE() {
		AnnotationConversions.getNonNullCorrectAnno().toString();
	}
}

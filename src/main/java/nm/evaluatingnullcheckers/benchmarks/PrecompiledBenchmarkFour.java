package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;
import nm.precompiledclasses.AnnotationConversions;

@Annotated
@Interprocedural
@ReturnSource
@ObjectNPE
@NPEProne
public class PrecompiledBenchmarkFour {
	public static void throwNPE() {
		AnnotationConversions.getNullIncorrectAnno().toString();
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarkseven
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@MethodParameterSource
@GenericsNPE
@NPEProne
public class BenchmarkThirtyOne {

	public static <T> void throwNPE(T object){
		object.toString();
	}

}

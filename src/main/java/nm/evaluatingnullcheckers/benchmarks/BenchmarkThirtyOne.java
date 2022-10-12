package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Parameter;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkseven
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Parameter
@GenericsNPE
@NPEProne
@Imperative
public class BenchmarkThirtyOne {

	public static <T> void throwNPE(T object){
		object.toString();
	}

}

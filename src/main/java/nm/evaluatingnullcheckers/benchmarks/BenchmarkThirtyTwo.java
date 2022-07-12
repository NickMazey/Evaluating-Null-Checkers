package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarkeight
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@LocalSource
@GenericsNPE
@NPEProne
public class BenchmarkThirtyTwo {
	public static <T> void throwNPE(){
		T object = null;
		object.toString();
	}
}

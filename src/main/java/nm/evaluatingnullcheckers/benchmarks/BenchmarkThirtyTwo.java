package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkeight
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Local
@GenericsNPE
@NPEProne
@Imperative
public class BenchmarkThirtyTwo {
	public static <T> void throwNPE(){
		T object = null;
		object.toString();
	}
}

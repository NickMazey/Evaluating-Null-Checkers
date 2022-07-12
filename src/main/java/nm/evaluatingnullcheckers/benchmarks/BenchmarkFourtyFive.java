package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedFalse;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmarktwentyone
 * 
 * @author Nick Mazey
 *
 * @param <T> - Generic type
 */
@Nonannotated
@Intraprocedural
@FieldSource
@GenericsNPE
@ExpectedFalse
public class BenchmarkFourtyFive <T> {
	
	T object = null;
	
	public BenchmarkFourtyFive() {}

	public String throwNPE() {
		if(object != null) {
		return object.toString();
		}
		return null;
	}

}

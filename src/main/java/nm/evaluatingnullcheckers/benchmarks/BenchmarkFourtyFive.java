package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
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
@NPEProof
public class BenchmarkFourtyFive <T> {
	
	T object = null;
	
	public BenchmarkFourtyFive() {}

	public void throwNPE() {
		if(object != null) {
			object.toString();
		}
	}

}

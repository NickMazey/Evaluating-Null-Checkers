package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedFalse;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;

/**
 * Non-annotated version of benchmark sixteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@FieldSource
@ArrayNPE
@ExpectedFalse
public class BenchmarkFourty {
	int[] numbers = null;
	
	public BenchmarkFourty() {}
	
	public int throwNPE() {
		if(numbers != null) {
			return numbers[0];
		}
		return 0;
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@FieldSource
@ObjectNPE
@ExpectedTrue
public class BenchmarkThree {
	@Nullable
	String name = null;
	
	public BenchmarkThree() {}
	
	public int throwNPE() {
		return name.length();
	}

}

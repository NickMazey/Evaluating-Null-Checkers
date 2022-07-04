package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@MethodParameterSource
@GenericsNPE
@ExpectedTrue
public class BenchmarkSeven {

	public static <T> void throwNPE(@Nullable T object){
		object.toString();
	}

}

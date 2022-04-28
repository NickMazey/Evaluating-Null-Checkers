package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;

@Annotated
@Intraprocedural
@MethodParameterSource
@GenericsNPE
@ExpectedTrue
//TODO: Write benchmark to use the generic object to throw an NPE
public class BenchmarkSeven {
	
	public static <T> void throwNPE(T object) {
		throw new NullPointerException();
	}

}

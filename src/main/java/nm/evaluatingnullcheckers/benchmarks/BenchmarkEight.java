package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;

@Annotated
@Intraprocedural
@LocalSource
@GenericsNPE
@ExpectedTrue
public class BenchmarkEight {
	public static <T> void throwNPE(){
		T object = null;
		object.toString();
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@ReturnSource
@ObjectNPE
@ExpectedTrue
public class BenchmarkTen {
	
	private static @Nullable Object getObject() {
		return null;
	}
	
	public static void throwNPE() {
		getObject().toString();
	}
}

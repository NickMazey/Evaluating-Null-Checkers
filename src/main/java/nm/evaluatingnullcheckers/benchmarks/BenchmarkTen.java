package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ReturnSource;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@ReturnSource
@ObjectNPE
@NPEProne
public class BenchmarkTen {
	
	private static @Nullable Object getObject() {
		return null;
	}
	
	public static void throwNPE() {
		getObject().toString();
	}
}

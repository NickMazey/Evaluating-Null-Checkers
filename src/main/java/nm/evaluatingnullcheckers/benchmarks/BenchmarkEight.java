package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Generic;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Local;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@Generic
@NPEProne
@Imperative
public class BenchmarkEight {
	public static <T> void throwNPE(){
		@Nullable T object = null;
		object.toString();
	}
}

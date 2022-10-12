package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * 
 * @author Nick Mazey
 *
 * @param <T> - GenericVar type
 */
@Annotated
@Intraprocedural
@Field
@GenericVar
@NPEProne
@Imperative
public class BenchmarkNine <T> {
	
	@Nullable T object = null;
	
	public BenchmarkNine() {}

	public void throwNPE() {
		object.toString();
	}

}

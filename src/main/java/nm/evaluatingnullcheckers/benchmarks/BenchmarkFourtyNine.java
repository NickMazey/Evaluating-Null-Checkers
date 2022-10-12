package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Object;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Return;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@Return
@Object
@NPEProne
@Imperative
public class BenchmarkFourtyNine {
	class A{
		java.lang.Object foo() {
			return new java.lang.Object();
		}
	}
	
	class B extends A{
		@Override
		@Nullable
		java.lang.Object foo() {
			return null;
		}
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;

/**
 * Subject violating LSP
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Interprocedural
@LocalSource
@ObjectNPE
@NPEProne
public class BenchmarkFifty {
	class A{
		Object foo() {
			return new Object();
		}
	}
	
	class B extends A{
		@Override
		@Nullable Object foo() {
			return null;
		}
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
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
@NPEProof
public class BenchmarkFiftyOne {
	class A{
		@Nullable Object foo(@Nonnull Object obj) {
			return obj;
		}
	}
	
	public static void throwNPE() {
		BenchmarkFiftyOne b = new BenchmarkFiftyOne();
		b.new A().foo(new Object()).toString();
	}
	
	
}

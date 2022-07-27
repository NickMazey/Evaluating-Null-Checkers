package nm.evaluatingnullcheckers.benchmarks;

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
public class FlowAnalysisBenchmarkOne {
	
	class A{
		@Nullable Object foo() {
			return new Object();
		}
	}
	
	public static void throwNPE() {
		FlowAnalysisBenchmarkOne b = new FlowAnalysisBenchmarkOne();
		b.new A().foo().toString();
	}
	
	
}

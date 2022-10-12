package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectVar;
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
@ObjectVar
@NPEProof
@Imperative
public class FlowAnalysisBenchmarkOne {
	
	class A{
		@Nullable
		java.lang.Object foo() {
			return new java.lang.Object();
		}
	}
	
	public static void throwNPE() {
		FlowAnalysisBenchmarkOne b = new FlowAnalysisBenchmarkOne();
		b.new A().foo().toString();
	}
	
	
}

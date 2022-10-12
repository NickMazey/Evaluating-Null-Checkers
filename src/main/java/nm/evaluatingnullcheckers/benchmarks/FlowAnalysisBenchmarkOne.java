package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
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
@ObjectNPE
@NPEProof
@Imperative
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

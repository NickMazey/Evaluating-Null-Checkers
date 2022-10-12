package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nonnull;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
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
@NPEProne
@Imperative
public class FlowAnalysisBenchmarkTwo {

	class A{
		@Nonnull
		java.lang.Object foo() {
			return null;
		}
	}
	
	public static void throwNPE() {
		FlowAnalysisBenchmarkTwo b = new FlowAnalysisBenchmarkTwo();
		b.new A().foo().toString();
	}
}

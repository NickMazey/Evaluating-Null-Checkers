package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nonnull;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Interprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
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
public class FlowAnalysisBenchmarkTwo {

	class A{
		@Nonnull Object foo() {
			return null;
		}
	}
	
	public static void throwNPE() {
		FlowAnalysisBenchmarkTwo b = new FlowAnalysisBenchmarkTwo();
		b.new A().foo().toString();
	}
}

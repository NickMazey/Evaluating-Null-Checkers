package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;


/**
 * Non-annotated version of benchmarkfifteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Field
@ObjectNPE
@NPEProof
@Imperative
public class BenchmarkThirtyNine {
	String name = null;
	
	public BenchmarkThirtyNine() {}
	
	public void throwNPE() {
		if(name != null) {
			int i = name.length();
		}
	}

}

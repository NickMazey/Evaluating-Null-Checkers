package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;

/**
 * Non-annotated version of benchmarkfifteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@FieldSource
@ObjectNPE
@NPEProof
public class BenchmarkThirtyNine {
	String name = null;
	
	public BenchmarkThirtyNine() {}
	
	public void throwNPE() {
		if(name != null) {
			int i = name.length();
		}
	}

}

package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProof;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectVar;

/**
 * Null-guarded version of benchmarkthree
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Field
@ObjectVar
@NPEProof
@Imperative
public class BenchmarkFifteen {
	@Nullable
	String name = null;
	
	public BenchmarkFifteen() {}
	
	public void throwNPE() {
		if(name != null) {
			int i = name.length();
		}
	}

}

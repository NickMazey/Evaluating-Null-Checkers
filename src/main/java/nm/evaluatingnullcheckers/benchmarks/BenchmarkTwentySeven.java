package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Field;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectVar;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Imperative;

/**
 * Non-annotated version of benchmarkthree
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Field
@ObjectVar
@NPEProne
@Imperative
public class BenchmarkTwentySeven {
	String name = null;
	
	public BenchmarkTwentySeven() {}
	
	public void throwNPE() {
		int i = name.length();
	}

}

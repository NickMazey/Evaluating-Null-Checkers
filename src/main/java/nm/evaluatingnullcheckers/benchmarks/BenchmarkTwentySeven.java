package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.FieldSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Nonannotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ObjectNPE;

/**
 * Non-annotated version of benchmarkthree
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@FieldSource
@ObjectNPE
@NPEProne
public class BenchmarkTwentySeven {
	String name = null;
	
	public BenchmarkTwentySeven() {}
	
	public int throwNPE() {
		return name.length();
	}

}

package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * Non-annotated version of benchmarkthirteen
 * 
 * @author Nick Mazey
 *
 */
@Nonannotated
@Intraprocedural
@Local
@ObjectVar
@NPEProof
@Imperative
public class BenchmarkThirtySeven {

	public static void throwNPE() {
		String str = null;
		if(str != null) {
			str.length();
		}
	}
}

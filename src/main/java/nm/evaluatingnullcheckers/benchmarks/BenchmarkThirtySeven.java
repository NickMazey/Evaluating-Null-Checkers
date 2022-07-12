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
@LocalSource
@ObjectNPE
@ExpectedFalse
public class BenchmarkThirtySeven {

	public static int throwNPE() {
		String str = null;
		if(str != null) {
			return str.length();
		}
		return 0;
	}
}

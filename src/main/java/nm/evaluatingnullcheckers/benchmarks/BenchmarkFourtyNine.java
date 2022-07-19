package nm.evaluatingnullcheckers.benchmarks;




import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ArrayNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.LocalSource;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.NPEProne;

/**
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ArrayNPE
@NPEProne
public class BenchmarkFourtyNine {
	public static void throwNPE() {
		@Nonnull Object @Nullable[] arr = new Object[10];
		arr[0].toString();
	}
}

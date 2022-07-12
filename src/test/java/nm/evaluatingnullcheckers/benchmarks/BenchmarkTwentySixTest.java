package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTwentySix;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that BenchmarkTwentySix can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTwentySixTest {
	
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkTwentySix.throwNPE();
		});
	}
}

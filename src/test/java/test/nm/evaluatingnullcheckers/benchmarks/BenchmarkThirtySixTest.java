package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThirtySix;


/**
 * JUnit test to validate that BenchmarkThirtySix can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkThirtySixTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkThirtySix.throwNPE();
		});
	}
}

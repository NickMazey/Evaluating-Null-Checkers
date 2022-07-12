package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThirtyOne;


/**
 * JUnit test to validate that BenchmarkThirtyOne can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkThirtyOneTest {
	@Test
	public void ThrowsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkThirtyOne.throwNPE(null);
		});
	}
}

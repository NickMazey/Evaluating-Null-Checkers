package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThree;

/**
 * JUnit test to validate that BenchmarkThree can throw an NPE
 * @author nickmazey
 *
 */
public class BenchmarkThreeTest {

		@Test
		public void throwsNPE() {
			assertThrows(NullPointerException.class,()->{
				BenchmarkThree bench = new BenchmarkThree();
				bench.throwNPE();
			});
		}
}

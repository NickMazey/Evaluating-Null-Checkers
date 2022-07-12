package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTwentyEight;

/**
 * JUnit test to validate that BenchmarkFour can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTwentyEightTest {

		@Test
		public void throwsNPE() {
			assertThrows(NullPointerException.class,()->{
				BenchmarkTwentyEight bench = new BenchmarkTwentyEight();
				bench.throwNPE();
			});
		}
}

package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkFour;

/**
 * JUnit test to validate that BenchmarkFour can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkFourTest {

		@Test
		public void throwsNPE() {
			assertThrows(NullPointerException.class,()->{
				BenchmarkFour bench = new BenchmarkFour();
				bench.throwNPE();
			});
		}
}

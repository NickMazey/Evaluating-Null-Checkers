package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test to validate that LambdaBenchmarkFour can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkFourTest {

		@Test
		public void throwsNPE() {
			assertThrows(NullPointerException.class,()->{
				LambdaBenchmarkFour bench = new LambdaBenchmarkFour();
				bench.throwNPE.run();
			});
		}
}

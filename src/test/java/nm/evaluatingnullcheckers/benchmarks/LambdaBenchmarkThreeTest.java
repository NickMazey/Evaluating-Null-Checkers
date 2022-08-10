package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test to validate that LambdaBenchmarkThree can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkThreeTest {

		@Test
		public void throwsNPE() {
			assertThrows(NullPointerException.class,()->{
				LambdaBenchmarkThree bench = new LambdaBenchmarkThree();
				bench.throwNPE.run();
			});
		}
}

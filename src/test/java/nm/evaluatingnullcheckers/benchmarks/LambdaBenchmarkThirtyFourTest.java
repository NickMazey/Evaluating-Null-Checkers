package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that LambdaBenchmarkThirtyFour can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkThirtyFourTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			LambdaBenchmarkThirtyFour.throwNPE.run();
		});
	}
}

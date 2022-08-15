package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test to validate that LambdaBenchmarkEight can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkEightTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			new LambdaBenchmarkEight<Object>().throwNPE.run();
		});
	}
}

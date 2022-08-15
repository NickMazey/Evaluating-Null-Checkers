package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that LambdaBenchmarkNine can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkNineTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			LambdaBenchmarkNine.throwNPE.run();
		});
	}
}

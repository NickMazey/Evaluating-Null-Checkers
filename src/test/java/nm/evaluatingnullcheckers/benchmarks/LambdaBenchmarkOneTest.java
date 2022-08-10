package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that LambdaBenchmarkOne can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkOneTest {
	
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			LambdaBenchmarkOne.throwNPE.run();
		});
	}
}

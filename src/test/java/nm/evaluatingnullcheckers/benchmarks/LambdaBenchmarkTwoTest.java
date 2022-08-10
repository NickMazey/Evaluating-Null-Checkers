package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that LambdaBenchmarkTwo can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkTwoTest {
	
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			LambdaBenchmarkTwo.throwNPE.run();
		});
	}
}
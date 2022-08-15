package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that LambdaBenchmarkTen can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkTenTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			LambdaBenchmarkTen.throwNPE.run();
		});
	}
}

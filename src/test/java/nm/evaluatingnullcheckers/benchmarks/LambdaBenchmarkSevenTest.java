package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test to validate that LambdaBenchmarkSeven can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkSevenTest {
	@Test
	public void ThrowsNPE() {
		assertThrows(NullPointerException.class,()->{
			LambdaBenchmarkSeven.throwNPE.accept(null);
		});
	}
}

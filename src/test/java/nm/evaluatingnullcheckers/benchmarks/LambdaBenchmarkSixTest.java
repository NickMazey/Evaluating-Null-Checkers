package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test to validate that LambdaBenchmarkSix can throw an NPE
 * @author Nick Mazey
 *
 */
public class LambdaBenchmarkSixTest {
	@Test
	public void ThrowsNPE() {
		assertThrows(NullPointerException.class,()-> LambdaBenchmarkSix.FirstIndex.accept(null));
	}

}

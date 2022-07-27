package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * JUnit test to validate that PrecompiledBenchmarkFour can throw an NPE
 * @author Nick Mazey
 *
 */
public class PrecompiledBenchmarkFourTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			PrecompiledBenchmarkFour.throwNPE();
		});
	}
}

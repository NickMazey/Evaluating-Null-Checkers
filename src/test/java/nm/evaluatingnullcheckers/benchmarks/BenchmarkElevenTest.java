package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkEleven;

/**
 * JUnit test to validate that BenchmarkEleven can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkElevenTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class, BenchmarkEleven::throwNPE);
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThirtyTwo;


/**
 * JUnit test to validate that BenchmarkThirtyTwo can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkThirtyTwoTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class, BenchmarkThirtyTwo::throwNPE);
	}
}

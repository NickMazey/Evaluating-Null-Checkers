package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThirtyFour;


/**
 * JUnit test to validate that BenchmarkThirtyFour can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkThirtyFourTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class, BenchmarkThirtyFour::throwNPE);
	}
}

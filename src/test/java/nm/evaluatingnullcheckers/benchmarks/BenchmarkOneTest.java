package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkOne;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that BenchmarkOne can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkOneTest {
	
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class, BenchmarkOne::throwNPE);
	}
}

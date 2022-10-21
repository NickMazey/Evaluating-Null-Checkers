package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTwentyNine;

/**
 * JUnit test to validate that BenchmarkTwentyNine can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTwentyNineTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()-> BenchmarkTwentyNine.StringLength(null));
	}
}


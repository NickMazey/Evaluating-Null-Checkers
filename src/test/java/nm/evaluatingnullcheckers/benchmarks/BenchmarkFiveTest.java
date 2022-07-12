package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkFive;

/**
 * JUnit test to validate that BenchmarkFive can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkFiveTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkFive.StringLength(null);
		});
	}
}


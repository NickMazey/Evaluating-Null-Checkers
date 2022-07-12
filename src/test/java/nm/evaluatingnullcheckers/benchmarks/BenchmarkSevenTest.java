package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkSeven;

/**
 * JUnit test to validate that BenchmarkSeven can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkSevenTest {
	@Test
	public void ThrowsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkSeven.throwNPE(null);
		});
	}
}

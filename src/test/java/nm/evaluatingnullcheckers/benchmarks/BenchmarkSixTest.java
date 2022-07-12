package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkSix;

/**
 * JUnit test to validate that BenchmarkSix can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkSixTest {
	@Test
	public void ThrowsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkSix.FirstIndex(null);
		});
	}

}

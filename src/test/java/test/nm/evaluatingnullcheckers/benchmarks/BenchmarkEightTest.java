package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkEight;
import nm.evaluatingnullcheckers.benchmarks.BenchmarkFive;

/**
 * JUnit test to validate that BenchmarkEight can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkEightTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkEight.<Object>throwNPE();
		});
	}
}

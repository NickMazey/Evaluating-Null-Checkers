package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTen;

/**
 * JUnit test to validate that BenchmarkTen can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTenTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkTen.throwNPE();
		});
	}
}

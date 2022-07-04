package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTwelve;

/**
 * JUnit test to validate that BenchmarkTwelve can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTwelveTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkTwelve.throwNPE();
		});
	}
}

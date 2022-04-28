package test.nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTwo;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that BenchmarkTwo can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTwoTest {
	
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkTwo.throwNPE();
		});
	}
}
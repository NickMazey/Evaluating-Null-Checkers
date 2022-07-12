package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkNine;

/**
 * JUnit test to validate that BenchmarkNine can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkNineTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			new BenchmarkNine<Object>().throwNPE();
		});
	}
}

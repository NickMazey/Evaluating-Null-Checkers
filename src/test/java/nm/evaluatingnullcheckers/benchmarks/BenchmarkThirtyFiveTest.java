package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThirtyFive;


/**
 * JUnit test to validate that BenchmarkThirtyFive can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkThirtyFiveTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkThirtyFive.throwNPE();
		});
	}
}

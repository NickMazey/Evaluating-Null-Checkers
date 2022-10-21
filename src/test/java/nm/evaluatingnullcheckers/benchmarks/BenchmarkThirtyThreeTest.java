package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThirtyThree;

/**
 * JUnit test to validate that BenchmarkThirtyThree can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkThirtyThreeTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()-> new BenchmarkThirtyThree<>().throwNPE());
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * JUnit test to validate that BenchmarkFourtyNine can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkFourtyNineTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkFourtyNine b = new BenchmarkFourtyNine();
			b.new B().foo().toString();
		});
	}
}

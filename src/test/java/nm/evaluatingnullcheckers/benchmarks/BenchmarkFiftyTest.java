package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * JUnit test to validate that BenchmarkFifty can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkFiftyTest {
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkFifty b = new BenchmarkFifty();
			b.new B().foo().toString();
		});
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTwentySeven;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that BenchmarkTwentySeven can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTwentySevenTest {
	
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkTwentySeven bench = new BenchmarkTwentySeven();
			bench.throwNPE();
		});
	}
}

package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkTwentyFive;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * JUnit test to validate that BenchmarkTwentyFive can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkTwentyFiveTest {
	
	@Test
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkTwentyFive.throwNPE();
		});
	}
}

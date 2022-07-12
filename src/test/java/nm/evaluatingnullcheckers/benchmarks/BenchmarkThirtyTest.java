package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkThirty;


/**
 * JUnit test to validate that BenchmarkThirty can throw an NPE
 * 
 * @author Nick Mazey
 *
 */
public class BenchmarkThirtyTest {
	@Test
	public void ThrowsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkThirty.FirstIndex(null);
		});
	}

}

package test.nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import nm.evaluatingnullcheckers.benchmarks.BenchmarkSeven;

/**
 * JUnit test to validate that BenchmarkSeven can throw an NPE
 * @author Nick Mazey
 *
 */
public class BenchmarkSevenTest {
	@Test
	public void ThrowsNPE() {
		assertThrows(NullPointerException.class,()->{
			BenchmarkSeven.throwNPE(null);
		});
	}
}

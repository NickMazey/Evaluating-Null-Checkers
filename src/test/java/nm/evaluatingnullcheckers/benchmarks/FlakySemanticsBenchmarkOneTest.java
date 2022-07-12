package nm.evaluatingnullcheckers.benchmarks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import nm.evaluatingnullcheckers.benchmarks.FlakySemanticsBenchmarkOne;


/**
 * JUnit test to validate that FlakySemanticsBenchmarkOne can throw an NPE
 * 
 * @author Nick Mazey
 *
 */
public class FlakySemanticsBenchmarkOneTest {
	
	@Test
	@Timeout(1000)
	/**
	 * Unusual test, will pass if each loop's execution time is less than 1ms
	 * timeout has been added in the event of infinite recursion
	 */
	public void throwsNPE() {
		assertThrows(NullPointerException.class,()->{
			while(true) {
				FlakySemanticsBenchmarkOne.throwNPE();
				}
		});
	}
}

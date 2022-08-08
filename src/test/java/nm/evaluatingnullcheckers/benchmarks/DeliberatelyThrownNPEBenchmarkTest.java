package nm.evaluatingnullcheckers.benchmarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test to validate that DeliberatelyThrownNPEBenchmark can throw an NPE
 * @author Nick Mazey
 *
 */
public class DeliberatelyThrownNPEBenchmarkTest {
    @Test
    public void throwsNPE() {
        assertThrows(NullPointerException.class,()->{
            DeliberatelyThrownNPEBenchmark.throwNPE();
        });
    }
}

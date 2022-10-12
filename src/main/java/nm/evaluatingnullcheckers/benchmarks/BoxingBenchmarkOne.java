package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * Benchmark to see how checkers handle unboxing
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@ObjectVar
@NPEProne
@Imperative
public class BoxingBenchmarkOne {
    public static void throwNPE(){
        @Nullable Integer in = null;
        int i = in;
    }
}

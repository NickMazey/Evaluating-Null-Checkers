package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@ObjectNPE
@NPEProne
@Imperative
public class BoxingBenchmarkTwo {
    public static void throwNPE(@Nullable Integer in){
        int i = in;
    }
}

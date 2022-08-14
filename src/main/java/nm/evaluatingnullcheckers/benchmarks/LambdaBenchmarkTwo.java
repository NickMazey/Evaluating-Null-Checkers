package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Lambda version of BenchmarkTwo
 *
 * @author Nick Mazey
 */
@Annotated
@Intraprocedural
@LocalSource
@ArrayNPE
@NPEProne
public class LambdaBenchmarkTwo {
    public static Runnable throwNPE = () -> {
        {
            @Nullable
            int[] numbers = null;
            int i = numbers[0];
        }
    };
}
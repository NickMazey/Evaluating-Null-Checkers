package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Null-guarded version of LambdaBenchmarkTwo
 *
 * @author Nick Mazey
 */
@Annotated
@Intraprocedural
@Local
@Array
@NPEProof
@Lambda
public class LambdaBenchmarkThirteen {
    public static Runnable throwNPE = () -> {
        {
            @Nullable
            int[] numbers = null;
            if(numbers != null && numbers.length > 0) {
                int i = numbers[0];
            }
        }
    };
}

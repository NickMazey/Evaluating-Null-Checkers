package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Lambda version of BenchmarkOne
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ObjectNPE
@NPEProne
public class LambdaBenchmarkOne {
    public static Runnable throwNPE = ()->{
        @Nullable
        Object obj = null;
        obj.toString();
    };
}

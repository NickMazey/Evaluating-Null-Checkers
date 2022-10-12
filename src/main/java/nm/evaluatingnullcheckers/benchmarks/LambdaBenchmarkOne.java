package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.lang.Object;

/**
 * Lambda version of BenchmarkOne
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@ObjectVar
@NPEProne
@Lambda
public class LambdaBenchmarkOne {
    public static Runnable throwNPE = ()->{
        @Nullable
        Object obj = null;
        obj.toString();
    };
}

package nm.evaluatingnullcheckers.benchmarks;


import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.lang.Object;

/**
 * Null-guarded version of LambdaBenchmarkOne
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@ObjectVar
@NPEProof
@Lambda
public class LambdaBenchmarkTwelve {
    public static Runnable throwNPE = ()->{
        @Nullable
        Object obj = null;
        if(obj != null) {
            obj.toString();
        }
    };
}

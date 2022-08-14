package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;

/**
 * Test to see how the checkers handle exceptions
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ObjectNPE
@NPEProof
public class ExceptionBenchmarkOne {
    public static void preventNPE(@Nullable Object obj){
        if(obj == null){
            throw new IllegalArgumentException();
        }
        obj.toString();
    }
}

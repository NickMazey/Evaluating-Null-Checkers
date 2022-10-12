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
@Local
@ObjectNPE
@NPEProof
//Brings into question the meaning of "NPEProof"
@Imperative
public class ExceptionBenchmarkTwo {
    public static void preventNPE(@Nullable Object object){
        try{
            object.toString();
        }catch(NullPointerException e){

        }
    }
}

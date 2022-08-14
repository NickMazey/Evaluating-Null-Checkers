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
@LocalSource
@ObjectNPE
@NPEProof
//Brings into question the meaning of "NPEProof"
public class ExceptionBenchmarkTwo {
    public static void preventNPE(@Nullable Object object){
        try{
            object.toString();
        }catch(NullPointerException e){

        }
    }
}

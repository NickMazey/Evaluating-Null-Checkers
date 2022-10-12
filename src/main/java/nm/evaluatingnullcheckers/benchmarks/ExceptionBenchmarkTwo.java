package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.lang.Object;

/**
 *
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Local
@BenchmarkAnnotations.Object
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

package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Null-guarded version of LambdaBenchmarkSeven
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@MethodParameterSource
@GenericsNPE
@NPEProof
@Lambda
public class LambdaBenchmarkEighteen {

	public static Consumer<? extends Object> throwNPE = (@Nullable Object object) ->{
		if(object != null) {
			object.toString();
		}
	};

}

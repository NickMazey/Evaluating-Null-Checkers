package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Lambda version of BenchmarkSeven
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@GenericVar
@NPEProne
@Lambda
public class LambdaBenchmarkSeven {

	public static Consumer<? extends Object> throwNPE = (@Nullable Object object) ->{
		object.toString();
	};

}

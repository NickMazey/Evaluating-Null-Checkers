package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Lambda version of BenchmarkSix
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@Array
@NPEProne
@Lambda
public class LambdaBenchmarkSix {
	public static Consumer<Object[]> FirstIndex = (@Nullable Object[] arr) ->{
		Object o = arr[0];
	};
}

package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Null-guarded version of LambdaBenchmarkSix
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@ArrayVar
@NPEProof
@Lambda
public class LambdaBenchmarkSeventeen {
	public static Consumer<Object[]> FirstIndex = (@Nullable Object[] arr) ->{
		if(arr != null && arr.length > 0) {
			Object o = arr[0];
		}
	};
}

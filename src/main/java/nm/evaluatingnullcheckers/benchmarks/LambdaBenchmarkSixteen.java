package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Null-guarded version of LambdaBenchmarkFive
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@Parameter
@BenchmarkAnnotations.Object
@NPEProof
@Lambda
public class LambdaBenchmarkSixteen {
	public static Consumer<String> StringLength = (@Nullable String str) -> {
		if(str != null) {
			int i = str.length();
		}
	};
}

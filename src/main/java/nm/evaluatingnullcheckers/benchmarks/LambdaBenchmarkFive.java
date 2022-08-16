package nm.evaluatingnullcheckers.benchmarks;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Lambda version of BenchmarkFive
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@MethodParameterSource
@ObjectNPE
@NPEProne
@Lambda
public class LambdaBenchmarkFive {
	public static Consumer<String> StringLength = (@Nullable String str) -> {
		int i = str.length();
	};
}

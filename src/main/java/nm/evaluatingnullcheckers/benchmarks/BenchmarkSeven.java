package nm.evaluatingnullcheckers.benchmarks;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Annotated;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.ExpectedTrue;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.GenericsNPE;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.Intraprocedural;
import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.MethodParameterSource;

@Annotated
@Intraprocedural
@MethodParameterSource
@GenericsNPE
@ExpectedTrue
public class BenchmarkSeven {

	//TODO: Improve to be more reliant on generics
	public static <T> void throwNPE(@Nullable T object){
		object.getClass();
	}

}

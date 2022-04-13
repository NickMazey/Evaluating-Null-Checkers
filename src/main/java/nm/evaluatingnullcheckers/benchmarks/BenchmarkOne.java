package nm.evaluatingnullcheckers.benchmarks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations.*;

/**
 * Simple benchmark that will throw an NPE if the "throwNPE" method is called
 * 
 * @author Nick Mazey
 *
 */
@Annotated
@Intraprocedural
@LocalSource
@ObjectNPE
@ExpectedTrue
public class BenchmarkOne {
  /**
   * Method designed to cause a null pointer exception
   */
  public static void throwNPE() {
    @Nullable String str = null;
    str.length();
   }
}

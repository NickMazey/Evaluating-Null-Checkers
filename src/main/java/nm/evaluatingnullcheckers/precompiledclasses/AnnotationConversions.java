package nm.evaluatingnullcheckers.precompiledclasses;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Class to see how the checkers handle pre-compiled classes
 * 
 * @author Nick Mazey
 *
 */
public class AnnotationConversions{

	public static @Nullable Object convertToNullable(@Nonnull Object object) {
		return object;
	}
	
	public static @Nonnull Object convertToNonNull(@Nullable Object object) {
		return object;
	}
	

	
	
}

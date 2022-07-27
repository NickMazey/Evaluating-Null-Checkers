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
	
	public static Object getNull() {
		return null;
	}
	
	public static Object getNonNull() {
		return new Object();
	}
	
	public static @Nullable Object getNullCorrectAnno() {
		return null;
	}
	
	public static @Nonnull Object getNullIncorrectAnno() {
		return null;
	}
	
	public static @Nonnull Object getNonNullCorrectAnno() {
		return new Object();
	}
	
	public static @Nullable Object getNonNullIncorrectAnno() {
		return new Object();
	}
	
	

	
	
}

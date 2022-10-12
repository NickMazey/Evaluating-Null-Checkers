package nm.evaluatingnullcheckers.tools;

/**
 * Auxiliary class for CheckerOutputParser to store data for parsing checkers
 *
 * @author Nick Mazey
 */
public final class OutputPattern {

    /**
     * Wrapped RunTimeException
     */
    public static final class InvalidOutputPatternException extends RuntimeException {
        InvalidOutputPatternException() {
        }

        InvalidOutputPatternException(String message) {
        }

        InvalidOutputPatternException(String message, Throwable cause) {
        }

        InvalidOutputPatternException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        }

        InvalidOutputPatternException(Throwable cause) {
        }
    }

    public final String vulnerableRegex;
    public final String errorRegex;
    public final String fileExtension;
    public final OutputPattern auxPattern;

    private static void nullError() {
        throw new InvalidOutputPatternException("Vulnerable Regex, Error Regex, and File Extension cannot be null.");
    }

    private static void regexError() {
        throw new InvalidOutputPatternException("Vulnerable and Error regexes must be distinct.");
    }

    private static void nestingError() {
        throw new InvalidOutputPatternException("Auxiliary pattern maximum depth of 5 cannot be exceeded.");
    }

    private OutputPattern(String vulnerableRegex, String errorRegex, String fileExtension, OutputPattern auxPattern) {
        this.vulnerableRegex = vulnerableRegex;
        this.errorRegex = errorRegex;
        this.fileExtension = fileExtension;
        this.auxPattern = auxPattern;
    }

    /**
     * Aliased OutputPattern factory
     *
     * @param vulnerableRegex - Regex for detecting a vulnerable classification
     * @param errorRegex      - Regex for detecting an error
     * @param fileExtension   - File extension for log
     * @return - OutputPattern constructed from the provided arguments
     * @throws InvalidOutputPatternException - If either regex is null, both regexes are identical, and if the auxiliary pattern is more than 4 patterns deep
     */
    public static OutputPattern of(String vulnerableRegex, String errorRegex, String fileExtension) {
        return of(vulnerableRegex, errorRegex, fileExtension, null);
    }

    /**
     * OutputPattern factory
     *
     * @param vulnerableRegex - Regex for detecting a vulnerable classification
     * @param errorRegex      - Regex for detecting an error
     * @param fileExtension   - File extension for log
     * @param auxPattern      - Pattern to check alongside this pattern (null if there is none)
     * @return - OutputPattern constructed from the provided arguments
     * @throws InvalidOutputPatternException - If either regex is null, both regexes are identical, and if the auxiliary pattern is more than 4 patterns deep
     */
    public static OutputPattern of(String vulnerableRegex, String errorRegex, String fileExtension, OutputPattern auxPattern) throws InvalidOutputPatternException {
        checkNesting(auxPattern);
        if (vulnerableRegex == null || errorRegex == null || fileExtension == null) {
            nullError();
        }
        if (vulnerableRegex.equals(errorRegex)) {
            regexError();
        }
        return new OutputPattern(vulnerableRegex, errorRegex, fileExtension, auxPattern);
    }

    private static void checkNesting(OutputPattern pattern) {
        for (int depth = 0; depth < 5; depth++) {
            if (pattern == null) {
                return;
            }
            pattern = pattern.auxPattern;
        }
        nestingError();
    }

    /**
     * Guarded getter for auxiliary pattern
     *
     * @return - This OutputPattern's auxiliary pattern
     * @throws UnsupportedOperationException - If this OutputPattern's auxiliary pattern is null
     */
    public OutputPattern auxiliaryPattern() throws UnsupportedOperationException {
        if (auxPattern == null) {
            throw new UnsupportedOperationException("Pattern has no auxiliary.");
        }
        return auxPattern;
    }
}

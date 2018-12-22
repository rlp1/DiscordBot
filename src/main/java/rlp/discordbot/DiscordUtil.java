package rlp.discordbot;

/**
 * @author rlp
 * @since 1.0
 */
public final class DiscordUtil {

    private DiscordUtil() { // not instantiated.
    }

    /**
     * This represents a static-final variable to a string, that is used to substitute the reiteration from empty strings.
     * @since 1.0
     */
    public static final String EMPTY_STRING = "";

    // ... Join Strings ...

    /**
     * This method join strings from an array with delimited ranges by start and end index.
     *
     * @param array the array that the strings will be provide.
     * @param startIndex the start index.
     * @param endIndex the end index.
     * @return the join from strings.
     */
    public static String joinStrings(final String[] array, final int startIndex, final int endIndex) {
        final StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            sb.append(array[i]).append(" ");
        }

        // @Note Delete the last character that represents (" "), that is appended in the last for-loop.
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * This method join strings from an array with delimited ranges by start and end index. This method is equals from
     * {@link #joinStrings(String[], int, int)} but doesn't contains the "endIndex", then in this method the end index
     * is defined as array length.
     *
     * @param array the array that the strings will be provide.
     * @param startIndex the start index.
     * @return the join from strings.
     */
    public static String joinStrings(final String[] array, final int startIndex) {
        return joinStrings(array, startIndex, array.length);
    }

    /**
     * This method join strings from an array with delimited ranges by start and end index. This method is equals from
     * {@link #joinStrings(String[], int, int)} but doesn't contains the "endIndex" and doesn't contains the "startIndex",
     * then in this method the start index by default is 0, and end index by default is array length.
     *
     * @param array the array that the strings will be provide.
     * @return the join from strings.
     */
    public static String joinStrings(final String[] array) {
        return joinStrings(array, 0);
    }
}

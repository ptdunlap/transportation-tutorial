package org.bananaforscale.util;

/**
 * A utility class for creating JSON based responses. Usually this would be done
 * with a bean and something like Jackson.
 */
public class JsonUtil {

    /**
     * Create a JSON wrapped success message
     *
     * @param message
     * @return
     */
    public static String createSuccessMessage(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"success\":")
                .append("\"").append(message).append("\"")
                .append("}");
        return sb.toString();
    }

    /**
     * Create a JSON wrapped error message
     *
     * @param reason
     * @return
     */
    public static String createErrorMessage(String reason) {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"error\":")
                .append("\"").append(reason).append("\"")
                .append("}");
        return sb.toString();
    }

    /**
     * Create a JSON wrapped generic message
     *
     * @param key
     * @param value
     * @return
     */
    public static String createJsonMessage(String key, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"" + key + "\":")
                .append("\"").append(value).append("\"")
                .append("}");
        return sb.toString();
    }

}

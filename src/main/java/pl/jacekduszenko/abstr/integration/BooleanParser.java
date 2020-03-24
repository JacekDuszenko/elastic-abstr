package pl.jacekduszenko.abstr.integration;

import pl.jacekduszenko.abstr.model.exception.TranslationException;

public class BooleanParser {
    private static final String TRUE_STRING = "true";
    private static final String FALSE_STRING = "false";

    public static Boolean parseBoolean(String value) throws TranslationException {
        if (isBooleanString(value)) {
            return Boolean.parseBoolean(value);
        } else {
            throw new TranslationException("Value is not boolean.");
        }
    }

    private static boolean isBooleanString(String value) {
        return TRUE_STRING.equals(value) || FALSE_STRING.equals(value);
    }
}

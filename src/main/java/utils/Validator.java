package utils;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern ID_PATTERN = Pattern.compile("\\d{5}");

    public static void checkId(String id) {
        if (!ID_PATTERN.matcher(id).matches()) {

        }
    }

}

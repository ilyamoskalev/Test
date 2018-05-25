package com.test.utils;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern ID_PATTERN = Pattern.compile("\\d{5}");
    private static final double LIMIT = 100000000d;

    public static boolean checkId(String id) {
        return ID_PATTERN.matcher(id).matches();
    }

    public static boolean ckeckSum(Double sum) {
        return sum > 0 && sum < LIMIT;
    }
}

package com.example.trialtask.util;

public class Utils {
    private static volatile Utils utils;

    private Utils() {}

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static void init() {
        if (utils == null)
            utils = new Utils();
    }

    /**
     * Check if any following Strings matches first String.
     * Its purpose is to avoid long if statements such as
     * if (str.equals(s1) || str.equals(s2) || str.equals(s3) ... )
     *
     * @param str
     * @param strs
     * @return
     */
    public static boolean anyStringEquals(String str, String ... strs) {
        init();
        for (String s : strs) {
            if (str.trim().equals(s.trim())) return true;
        }
        return false;
    }
}

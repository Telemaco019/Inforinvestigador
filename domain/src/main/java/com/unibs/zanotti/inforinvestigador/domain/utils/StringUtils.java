package com.unibs.zanotti.inforinvestigador.domain.utils;


import org.jetbrains.annotations.Nullable;

public class StringUtils {
    public static final String BLANK = "";

    public static boolean isBlank(@Nullable String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}

package com.belperier.eu4m.exporter.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public static long count(String rawLine, char characterToLookFor) {
        Pattern pattern = Pattern.compile("[^ \t]*"+characterToLookFor);
        Matcher matcher = pattern.matcher(rawLine);
        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
        //return rawLine.chars().filter(ch -> ch == characterToLookFor).count();
    }
}

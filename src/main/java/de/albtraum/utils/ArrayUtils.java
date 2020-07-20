package de.albtraum.utils;

public class ArrayUtils {
    public static boolean containsString(String[] array, String searchedString) {
        for (String s : array) {
            if (s.equals(searchedString)) return true;
        }
        return false;
    }
}


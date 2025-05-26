package de.ardania.urutar.ardaxpbottles.util;

public class Converter {

    public static Long tryParseLong(String str) {
        try {
            return Long.parseLong(str);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static Integer tryParseInteger(String str) {
        try {
            return Integer.parseInt(str);
        }
        catch (Exception ex) {
            return null;
        }
    }
}

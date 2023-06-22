package pl.timsixth.thetag.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtil {

    public static boolean isDouble(String arg) {
        try {
            Double.parseDouble(arg);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}

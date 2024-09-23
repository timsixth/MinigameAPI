package pl.timsixth.minigameapi.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EngineUtil {

    /**
     * Checks that current server is running on Papermc implementation or not
     *
     * @return true if the engine is paper otherwise false
     */
    public static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}

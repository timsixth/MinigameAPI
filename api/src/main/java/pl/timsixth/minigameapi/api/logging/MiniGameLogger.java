package pl.timsixth.minigameapi.api.logging;

import pl.timsixth.minigameapi.api.MiniGame;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class MiniGameLogger {

    private static Logger logger;
    private static String miniGameName;

    private MiniGameLogger() {
        MiniGame instance = MiniGame.getInstance();

        logger = instance.getLogger();
        miniGameName = "[" + instance.getName() + "]";
    }

    public static void info(String message) {
        logger.info(miniGameName + " " + message);
    }

    public static void error(String message) {
        logger.severe(miniGameName + " " + message);
    }

    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    public static void waring(String message) {
        logger.warning(miniGameName + " " + message);
    }
}

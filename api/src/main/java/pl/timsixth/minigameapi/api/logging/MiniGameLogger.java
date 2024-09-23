package pl.timsixth.minigameapi.api.logging;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.util.EngineUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logger for minigames.
 * This logger uses common java Logger.
 */
public final class MiniGameLogger {

    private static Logger logger;
    private static String miniGameName = "";

    private MiniGameLogger() {
        MiniGame instance = MiniGame.getInstance();

        logger = instance.getLogger();
        if (!EngineUtil.isPaper())
            miniGameName = "[" + instance.getName() + "]";
    }

    /**
     * Prints info information
     *
     * @param message info message
     */
    public static void info(String message) {
        logger.info(miniGameName + " " + message);
    }

    /**
     * Prints error message
     *
     * @param message error message
     */
    public static void error(String message) {
        logger.severe(miniGameName + " " + message);
    }

    /**
     * Prints error message with exception
     *
     * @param message   error message
     * @param throwable exception
     */
    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * Prints waring message
     *
     * @param message waring message
     */
    public static void waring(String message) {
        logger.warning(miniGameName + " " + message);
    }
}

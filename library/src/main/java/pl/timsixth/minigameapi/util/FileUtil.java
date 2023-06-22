package pl.timsixth.minigameapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.file.ConfigurationFile;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileUtil {
    /**
     * @param configurationFile class which contains information about file model
     * @return saved file
     */
    public static File createFile(ConfigurationFile configurationFile) {
        MiniGame miniGame = MiniGame.getInstance();
        createDataFolder(miniGame);
        File file = new File(miniGame.getDataFolder(), configurationFile.getName());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        }
        return file;
    }

    /**
     * Deletes file
     *
     * @param configurationFile the configuration file
     * @return true if everything was succeeded otherwise false
     */
    public static boolean deleteFile(ConfigurationFile configurationFile) {
        MiniGame miniGame = MiniGame.getInstance();
        createDataFolder(miniGame);
        File file = new File(miniGame.getDataFolder(), configurationFile.getName());

        return file.delete();
    }

    /**
     * Creates data folder
     *
     * @param miniGame minigame instance
     */
    private static void createDataFolder(MiniGame miniGame) {
        if (!miniGame.getDataFolder().mkdir()) {
            miniGame.getDataFolder().mkdirs();
        }
    }
}

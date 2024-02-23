package pl.timsixth.minigameapi.api.loader.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.loader.AbstractLoader;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

import java.io.File;
import java.util.function.BiConsumer;

/**
 * Template method for {@link FileLoader}
 *
 * @param <T> every class which implemented {@link FileModel}
 * @see Loader
 * @see Loaders
 */
public abstract class AbstractFileLoader<T extends Model> extends AbstractLoader<T> implements FileLoader<T> {

    /**
     * Loads file
     *
     * @param file           file to load
     * @param primarySection primary section in file
     * @param callback       runs add action in subclasses
     */
    protected void loadFile(File file, String primarySection, BiConsumer<String, ConfigurationSection> callback) {
        if (!file.exists()) {
            Bukkit.getLogger().warning("File " + file.getName() + " does not exists!");
            return;
        }

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = ConfigurationSectionUtil.getSection(yamlConfiguration, primarySection);

        for (String key : section.getKeys(false)) {
            callback.accept(key, section);
        }
    }

    /**
     * Loads file, by default parent path for file is plugin folder
     *
     * @param fileName       file's name
     * @param primarySection primary section in file
     * @param callback       runs add action in subclasses
     */
    protected void loadFile(String fileName, String primarySection, BiConsumer<String, ConfigurationSection> callback) {
        File file = new File(MiniGame.getInstance().getDataFolder(), fileName);

        loadFile(file, primarySection, callback);
    }
}

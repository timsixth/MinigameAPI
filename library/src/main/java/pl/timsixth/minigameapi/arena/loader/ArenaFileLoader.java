package pl.timsixth.minigameapi.arena.loader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.ArenaImpl;
import pl.timsixth.minigameapi.loader.file.AbstractFileLoader;
import pl.timsixth.minigameapi.util.ConfigurationSectionUtil;

import java.io.File;

/**
 * @see AbstractFileLoader
 * @see ArenaFileModel
 */
public class ArenaFileLoader extends AbstractFileLoader<ArenaFileModel> {
    /**
     * Loads arena from arenas.yml file
     */
    @Override
    public void load() {
        load("arenas.yml", "arenas");
    }

    /**
     * Loads data from file
     *
     * @param fileName       file to load
     * @param primarySection main section in file, This section is important to loading
     */
    @Override
    public void load(String fileName, String primarySection) {
        File file = new File(MiniGame.getInstance().getDataFolder(), fileName);
        if (!file.exists()) {
            Bukkit.getLogger().warning("File " + fileName + " does not exists!");
            return;
        }

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = ConfigurationSectionUtil.getSection(yamlConfiguration, primarySection);

        for (String key : section.getKeys(false)) {
            this.addObject(section.getObject(key, ArenaImpl.class));
        }
    }
}

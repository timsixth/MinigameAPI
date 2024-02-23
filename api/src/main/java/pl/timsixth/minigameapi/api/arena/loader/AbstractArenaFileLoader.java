package pl.timsixth.minigameapi.api.arena.loader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

import java.io.File;

@Deprecated
public abstract class AbstractArenaFileLoader extends AbstractFileLoader<Arena> implements ArenaLoader, ArenaFileLoader {

    /**
     * Loads arena file
     *
     * @param file           file to load
     * @param primarySection primary section in file
     * @deprecated
     */
    @Deprecated
    protected void loadFile(File file, String primarySection) {
        if (!file.exists()) {
            Bukkit.getLogger().warning("File " + file.getName() + " does not exists!");
            return;
        }

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = ConfigurationSectionUtil.getSection(yamlConfiguration, primarySection);

        for (String key : section.getKeys(false)) {
            this.addObject(section.getObject(key, getArenaClass()));
        }
    }

    /**
     * Gets class to serialize data from file
     *
     * @return class which extends Arena
     * @deprecated
     */
    protected abstract Class<? extends Arena> getArenaClass();

}

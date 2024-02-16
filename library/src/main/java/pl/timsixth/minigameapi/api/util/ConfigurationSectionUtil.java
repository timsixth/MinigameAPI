package pl.timsixth.minigameapi.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

@UtilityClass
public class ConfigurationSectionUtil {

    /**
     * Gets or creates primary section from file
     *
     * @param yamlConfiguration file to get primary section
     * @param name              section name
     * @return section
     */
    public static ConfigurationSection getSection(YamlConfiguration yamlConfiguration, String name) {
        if (yamlConfiguration.getConfigurationSection(name) == null) {
            return yamlConfiguration.createSection(name);
        }
        return yamlConfiguration.getConfigurationSection(name);
    }

    public static ConfigurationSection getSection(ConfigurationSection parent, String name) {
        if (parent.getConfigurationSection(name) == null) {
            return parent.createSection(name);
        }
        return parent.getConfigurationSection(name);
    }
}

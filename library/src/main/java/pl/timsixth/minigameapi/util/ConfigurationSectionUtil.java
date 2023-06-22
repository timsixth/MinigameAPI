package pl.timsixth.minigameapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

@UtilityClass
public class ConfigurationSectionUtil {


    public static ConfigurationSection getSection(YamlConfiguration yamlConfiguration, String name) {
        if (yamlConfiguration.getConfigurationSection(name) == null) {
            return yamlConfiguration.createSection(name);
        }
        return yamlConfiguration.getConfigurationSection(name);
    }

    public static ConfigurationSection getSection(ConfigurationSection parentSection,String name) {
        if (parentSection.getConfigurationSection(name) == null) {
            return parentSection.createSection(name);
        }
        return parentSection.getConfigurationSection(name);
    }
}

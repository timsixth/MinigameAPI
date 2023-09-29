package pl.timsixth.minigameapi.api.file.registration;

import lombok.Getter;
import pl.timsixth.minigameapi.api.file.ConfigurationFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Registration of {@link ConfigurationFile}.
 * This registration is important for bukkit serialization system
 */
public final class ConfigurationFileRegistration {

    @Getter
    private static final List<ConfigurationFile> configurationFiles = new ArrayList<>();

    private ConfigurationFileRegistration() {
    }

    /**
     * Registers configuration file to register this class in bukkit serialization
     *
     * @param configurationFile configurationFile to register
     */
    public static void registerConfigurationFile(ConfigurationFile configurationFile) {
        configurationFiles.add(configurationFile);
    }
}

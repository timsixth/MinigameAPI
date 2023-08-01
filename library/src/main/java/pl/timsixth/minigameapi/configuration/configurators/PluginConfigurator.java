package pl.timsixth.minigameapi.configuration.configurators;

import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.DefaultPluginConfiguration;

public class PluginConfigurator implements Configurator<DefaultPluginConfiguration> {

    /**
     * @return default configuration for plugin
     */
    @Override
    public DefaultPluginConfiguration configure() {
        return DefaultPluginConfiguration.builder()
                .useDataBase(true)
                .tablesPrefix("minigames_")
                .useDefaultStatsSystem(true)
                .build();
    }
}

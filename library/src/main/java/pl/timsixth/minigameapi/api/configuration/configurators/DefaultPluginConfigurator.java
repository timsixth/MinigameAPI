package pl.timsixth.minigameapi.api.configuration.configurators;

import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;
import pl.timsixth.minigameapi.api.configuration.Configurator;

public class DefaultPluginConfigurator implements Configurator<PluginConfiguration> {

    /**
     * @return default configuration for plugin
     */
    @Override
    public PluginConfiguration configure() {
        return PluginConfiguration.builder()
                .useDataBase(true)
                .tablesPrefix("minigames_")
                .useDefaultStatsSystem(false)
                .build();
    }
}

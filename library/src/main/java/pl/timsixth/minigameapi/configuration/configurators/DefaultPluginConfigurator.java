package pl.timsixth.minigameapi.configuration.configurators;

import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.PluginConfiguration;

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
                .useBoostersSystem(false)
                .build();
    }
}

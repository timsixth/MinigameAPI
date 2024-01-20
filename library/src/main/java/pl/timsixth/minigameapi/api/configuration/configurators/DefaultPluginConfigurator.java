package pl.timsixth.minigameapi.api.configuration.configurators;

import pl.timsixth.minigameapi.api.configuration.ArenaSaveType;
import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;

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
                .arenaSaveType(ArenaSaveType.SINGLE_FILE)
                .build();
    }
}

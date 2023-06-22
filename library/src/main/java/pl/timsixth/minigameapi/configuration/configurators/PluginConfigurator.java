package pl.timsixth.minigameapi.configuration.configurators;

import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.DefaultPluginConfiguration;

public class PluginConfigurator implements Configurator<DefaultPluginConfiguration> {
    @Override
    public DefaultPluginConfiguration configure() {
        return DefaultPluginConfiguration.builder()
                .useDataBase(false)
                .tablesPrefix("minigames_")
                .build();
    }
}

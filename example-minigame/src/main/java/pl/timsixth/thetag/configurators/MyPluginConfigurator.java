package pl.timsixth.thetag.configurators;

import pl.timsixth.minigameapi.configuration.configurators.PluginConfigurator;
import pl.timsixth.minigameapi.configuration.type.DefaultPluginConfiguration;

public class MyPluginConfigurator extends PluginConfigurator {

    @Override
    public DefaultPluginConfiguration configure() {
        return DefaultPluginConfiguration.builder()
                .tablesPrefix("thetag_")
                .useDataBase(true)
                .useDefaultStatsSystem(true)
                .build();
    }
}

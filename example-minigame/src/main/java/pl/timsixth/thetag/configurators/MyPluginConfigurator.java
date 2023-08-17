package pl.timsixth.thetag.configurators;

import pl.timsixth.minigameapi.api.configuration.configurators.DefaultPluginConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;

public class MyPluginConfigurator extends DefaultPluginConfigurator {

    @Override
    public PluginConfiguration configure() {
        return PluginConfiguration.builder()
                .tablesPrefix("thetag_")
                .useDataBase(true)
                .useDefaultStatsSystem(true)
                .build();
    }
}

package pl.timsixth.minigameapi.configuration.configurators;

import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.DefaultGameConfiguration;

public class GameConfigurator implements Configurator<DefaultGameConfiguration> {
    @Override
    public DefaultGameConfiguration configure() {
        return DefaultGameConfiguration.builder()
                .blockBreaking(true)
                .blocksPlacing(true)
                .useTeams(false)
                .droppingItems(false)
                .build();
    }
}

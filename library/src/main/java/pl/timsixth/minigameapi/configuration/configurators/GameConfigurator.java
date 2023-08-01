package pl.timsixth.minigameapi.configuration.configurators;

import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.DefaultGameConfiguration;

public class GameConfigurator implements Configurator<DefaultGameConfiguration> {

    /**
     * @return default configuration for game
     */
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

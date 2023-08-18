package pl.timsixth.minigameapi.api.configuration.configurators;

import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.api.configuration.Configurator;

public class DefaultGameConfigurator implements Configurator<GameConfiguration> {

    /**
     * @return default configuration for game
     */
    @Override
    public GameConfiguration configure() {
        return GameConfiguration.builder()
                .blockBreaking(true)
                .blocksPlacing(true)
                .useTeams(false)
                .droppingItems(false)
                .build();
    }
}

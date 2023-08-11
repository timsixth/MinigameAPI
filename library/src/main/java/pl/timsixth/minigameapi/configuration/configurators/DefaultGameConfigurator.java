package pl.timsixth.minigameapi.configuration.configurators;

import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.GameConfiguration;

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

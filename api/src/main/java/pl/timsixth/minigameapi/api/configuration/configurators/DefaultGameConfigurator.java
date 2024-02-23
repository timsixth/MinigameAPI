package pl.timsixth.minigameapi.api.configuration.configurators;

import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;

public class DefaultGameConfigurator implements Configurator<GameConfiguration> {

    /**
     * @return default configuration for game
     */
    @Override
    public GameConfiguration configure() {
        return GameConfiguration.builder()
                .blockBreaking(true)
                .blocksPlacing(true)
                .droppingItems(false)
                .build();
    }
}

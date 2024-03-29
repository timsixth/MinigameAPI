package pl.timsixth.exampleminigame.configurators;

import pl.timsixth.minigameapi.api.configuration.configurators.DefaultGameConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;

public class MyGameConfigurator extends DefaultGameConfigurator {

    @Override
    public GameConfiguration configure() {
        return GameConfiguration.builder()
                .blockBreaking(true)
                .droppingItems(false)
                .blocksPlacing(false)
                .build();
    }
}

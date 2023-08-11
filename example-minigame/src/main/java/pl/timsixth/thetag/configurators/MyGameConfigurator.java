package pl.timsixth.thetag.configurators;

import pl.timsixth.minigameapi.configuration.configurators.DefaultGameConfigurator;
import pl.timsixth.minigameapi.configuration.type.GameConfiguration;

public class MyGameConfigurator extends DefaultGameConfigurator {

    @Override
    public GameConfiguration configure() {
        return GameConfiguration
                .builder()
                .blockBreaking(false)
                .useTeams(false)
                .droppingItems(false)
                .blocksPlacing(false)
                .build();
    }
}

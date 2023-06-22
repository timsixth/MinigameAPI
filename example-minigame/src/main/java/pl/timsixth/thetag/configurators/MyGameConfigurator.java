package pl.timsixth.thetag.configurators;

import pl.timsixth.minigameapi.configuration.configurators.GameConfigurator;
import pl.timsixth.minigameapi.configuration.type.DefaultGameConfiguration;

public class MyGameConfigurator extends GameConfigurator {

    @Override
    public DefaultGameConfiguration configure() {
        return DefaultGameConfiguration
                .builder()
                .blockBreaking(false)
                .useTeams(false)
                .droppingItems(false)
                .blocksPlacing(false)
                .build();
    }
}

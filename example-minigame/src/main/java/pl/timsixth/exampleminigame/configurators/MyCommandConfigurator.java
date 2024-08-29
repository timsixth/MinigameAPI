package pl.timsixth.exampleminigame.configurators;

import pl.timsixth.minigameapi.api.configuration.configurators.DefaultCommandConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
@Deprecated
public class MyCommandConfigurator extends DefaultCommandConfigurator {

    @Override
    public CommandConfiguration configure() {
        return CommandConfiguration.builder()
                .doNotHavePermissionMessage("No permission")
                .onlyPlayersMessage("Only players can use this command")
                .build();
    }
}

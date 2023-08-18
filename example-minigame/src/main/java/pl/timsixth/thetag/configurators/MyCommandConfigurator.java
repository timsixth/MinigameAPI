package pl.timsixth.thetag.configurators;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultCommandConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.thetag.config.Messages;
@RequiredArgsConstructor
public class MyCommandConfigurator extends DefaultCommandConfigurator {

    private final Messages messages;

    @Override
    public CommandConfiguration configure() {
        return CommandConfiguration.builder()
                .doNotHavePermissionMessage(messages.getNoPermission())
                .onlyPlayersMessage("Only players can use this command")
                .build();
    }
}

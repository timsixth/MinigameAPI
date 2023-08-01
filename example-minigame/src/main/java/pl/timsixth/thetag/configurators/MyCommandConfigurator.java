package pl.timsixth.thetag.configurators;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.configuration.configurators.CommandConfigurator;
import pl.timsixth.minigameapi.configuration.type.DefaultCommandConfiguration;
import pl.timsixth.thetag.config.Messages;
@RequiredArgsConstructor
public class MyCommandConfigurator extends CommandConfigurator {

    private final Messages messages;

    @Override
    public DefaultCommandConfiguration configure() {
        return DefaultCommandConfiguration.builder()
                .doNotHavePermissionMessage(messages.getNoPermission())
                .onlyPlayersMessage("Only players can use this command")
                .build();
    }
}

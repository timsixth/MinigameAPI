package pl.timsixth.minigameapi.api.configuration.configurators;

import org.bukkit.ChatColor;
import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;


public class DefaultCommandConfigurator implements Configurator<CommandConfiguration> {

    /**
     * @return default configuration for commands
     */
    @Override
    public CommandConfiguration configure() {
        return CommandConfiguration.builder()
                .doNotHavePermissionMessage(ChatColor.RED + "You don't have permission")
                .onlyPlayersMessage("Only players can use this command")
                .build();
    }
}

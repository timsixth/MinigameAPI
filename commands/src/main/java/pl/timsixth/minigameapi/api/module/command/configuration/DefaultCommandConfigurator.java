package pl.timsixth.minigameapi.api.module.command.configuration;

import org.bukkit.ChatColor;
import pl.timsixth.minigameapi.api.configuration.Configurator;


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

package pl.timsixth.minigameapi.configuration.configurators;

import org.bukkit.ChatColor;
import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.DefaultCommandConfiguration;


public class CommandConfigurator implements Configurator<DefaultCommandConfiguration> {

    /**
     * @return default configuration for commands
     */
    @Override
    public DefaultCommandConfiguration configure() {
        return DefaultCommandConfiguration.builder()
                .doNotHavePermissionMessage(ChatColor.RED + "You don't have permission")
                .onlyPlayersMessage("Only players can use this command")
                .build();
    }
}

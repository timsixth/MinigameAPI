package pl.timsixth.minigameapi.api.module.command;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.module.command.configuration.CommandConfiguration;
import pl.timsixth.minigameapi.api.module.command.configuration.DefaultCommandConfigurator;

@Getter
public final class CommandsModule implements Module {

    private final CommandConfiguration commandConfiguration;
    private final CommandRegistration commandRegistration;

    public CommandsModule(JavaPlugin plugin) {
        this(plugin, new DefaultCommandConfigurator().configure());
    }

    public CommandsModule(JavaPlugin plugin, CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
        this.commandRegistration = new CommandRegistration(plugin, commandConfiguration);
    }

    @Override
    public String getName() {
        return "minigameapi-commands";
    }
}

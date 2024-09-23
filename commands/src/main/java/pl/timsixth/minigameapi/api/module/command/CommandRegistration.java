package pl.timsixth.minigameapi.api.module.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.api.module.command.configuration.CommandConfiguration;

import java.util.Objects;

/**
 * Registers commands and tab completer
 */
@RequiredArgsConstructor
public final class CommandRegistration {

    private final JavaPlugin plugin;
    private final CommandConfiguration commandConfiguration;

    /**
     * Registers command
     *
     * @param command command to register
     */
    public void registerCommand(ParentCommand command) {
        command.setCommandConfiguration(commandConfiguration);

        Objects.requireNonNull(plugin.getCommand(command.getName())).setExecutor(command);
    }

    /**
     * Registers command with tab completer
     *
     * @param command command to register
     */
    public void registerCommandWithTabCompleter(ParentCommand command) {
        if (command.getTabCompleter() == null) return;

        registerCommand(command);
        Objects.requireNonNull(plugin.getCommand(command.getName())).setTabCompleter(command.getTabCompleter());
    }
}

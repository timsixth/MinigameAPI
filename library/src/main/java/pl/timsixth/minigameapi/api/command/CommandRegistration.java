package pl.timsixth.minigameapi.api.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Registers commands and tab completer
 */
@RequiredArgsConstructor
public final class CommandRegistration {

    private final JavaPlugin plugin;

    /**
     * Registers command
     *
     * @param command command to register
     */
    public void registerCommand(ParentCommand command) {
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

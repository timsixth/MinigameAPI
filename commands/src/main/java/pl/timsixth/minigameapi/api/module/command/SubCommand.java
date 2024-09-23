package pl.timsixth.minigameapi.api.module.command;

import org.bukkit.command.CommandSender;

/**
 * Represents sub command
 */
public interface SubCommand {

    /**
     * Executes subcommand
     *
     * @param sender Source of the command
     * @param args   Passed command arguments
     * @return true if a valid command, otherwise false
     */
    boolean executeCommand(CommandSender sender, String[] args);

    /**
     * @return subcommand name
     */
    String getName();
}

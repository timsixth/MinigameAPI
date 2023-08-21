package pl.timsixth.minigameapi.api.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

/**
 * Represents sub command
 */
@Getter
@Setter
public abstract class SubCommand {

    private ParentCommand parentCommand;

    /**
     * Executes subcommand
     *
     * @param sender Source of the command
     * @param args   Passed command arguments
     * @return true if a valid command, otherwise false
     */
    protected abstract boolean executeCommand(CommandSender sender, String[] args);

    /**
     * @return subcommand name
     */
    public abstract String getName();
}

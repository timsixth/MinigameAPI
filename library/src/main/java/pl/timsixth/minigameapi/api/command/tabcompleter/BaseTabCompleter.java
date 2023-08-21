package pl.timsixth.minigameapi.api.command.tabcompleter;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.command.SubCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public abstract class BaseTabCompleter implements TabCompleter {

    private final ParentCommand parentCommand;
    private final List<Argument> arguments = new ArrayList<>();

    /**
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return list of completions
     */
    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> subCommandsNames = parentCommand.getSubCommands()
                    .stream().map(SubCommand::getName).collect(Collectors.toList());
            completions.addAll(subCommandsNames);
        }

        for (Argument argument : arguments) {
            if (args.length == argument.getArgsLength()) {
                if (argument.getConditions().isEmpty() && argument.getIndex() == -1) {
                    completions.addAll(argument.getValues());
                } else {
                    if (argument.getConditions().size() == 1) {
                        if (args[argument.getIndex()].equalsIgnoreCase(argument.getConditions().get(0))) {
                            completions.addAll(argument.getValues());
                        }
                    } else {
                        for (String condition : argument.getConditions()) {
                            if (args[argument.getIndex()].equalsIgnoreCase(condition)) {
                                completions.addAll(argument.getValues());
                            }
                        }
                    }
                }
            }
        }

        onTabComplete(sender, args);
        return completions;
    }

    /**
     * Adds new argument to completions
     *
     * @param argument argument to add
     */
    public void addArgument(Argument argument) {
        arguments.add(argument);
    }

    /**
     * @param sender Source of the command.  For players tab-completing a
     *               command inside of a command block, this will be the player, not
     *               the command block.
     * @param args   The arguments passed to the command, including final
     *               partial argument to be completed and command label
     */
    protected abstract void onTabComplete(CommandSender sender, String[] args);
}

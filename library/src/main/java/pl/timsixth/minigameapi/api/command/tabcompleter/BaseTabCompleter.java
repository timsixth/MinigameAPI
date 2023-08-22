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
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public abstract class BaseTabCompleter implements TabCompleter {

    private final ParentCommand parentCommand;
    private BiFunction<CommandSender, String[], List<String>> conditions;
    @Deprecated
    private final List<Argument> arguments = new ArrayList<>();

    /**
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return list of completions
     */
    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        if (args.length == 1) {
            return parentCommand.getSubCommands()
                    .stream().map(SubCommand::getName).collect(Collectors.toList());
        }

        if (conditions != null) {
            return conditions.apply(sender, args);
        }

        return Collections.emptyList();
    }

    /**
     * Adds conditions
     *
     * @param conditions callback to define conditions
     */
    public void addConditions(BiFunction<CommandSender, String[], List<String>> conditions) {
        this.conditions = conditions;
    }

    /**
     * Adds new argument to completions
     *
     * @param argument argument to add
     * @deprecated for removal 1.0.0-rc4
     */
    @Deprecated
    public void addArgument(Argument argument) {
        arguments.add(argument);
    }

    /**
     * @param sender Source of the command.  For players tab-completing a
     *               command inside a command block, this will be the player, not
     *               the command block.
     * @param args   The arguments passed to the command, including final
     *               partial argument to be completed and command label
     * @deprecated for removal 1.0.0-rc4
     */
    @Deprecated
    protected void onTabComplete(CommandSender sender, String[] args) {
        throw new UnsupportedOperationException("Not implemented");
    }
}

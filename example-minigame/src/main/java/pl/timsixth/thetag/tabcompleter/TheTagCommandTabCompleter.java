package pl.timsixth.thetag.tabcompleter;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import pl.timsixth.minigameapi.arena.Arena;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TheTagCommandTabCompleter implements TabCompleter {

    private final ArenaManager<ArenaFileModel> arenaManager;

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("join", "leave", "list", "stats", "randomjoin", "cosmeticshop"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            List<String> arenasNames = arenaManager.getArenas().stream()
                    .map(Arena::getName)
                    .collect(Collectors.toList());

            completions.addAll(arenasNames);
        }

        return completions;
    }
}

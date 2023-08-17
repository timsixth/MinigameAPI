package pl.timsixth.thetag.tabcompleter;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.ArenaFileModel;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AdminTheTagCommandTabCompleter implements TabCompleter {

    private final ArenaManager<ArenaFileModel> arenaManager;
    private final UserCoinsManager<UserCoinsDbModel> userCoinsManager;

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("create", "setspawn", "setlobby", "setgamelobby", "addcoins", "removecoins", "reload"));
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "setgamelobby":
                case "setspawn":
                    List<String> arenasNames = arenaManager.getArenas().stream()
                            .map(Arena::getName)
                            .collect(Collectors.toList());
                    completions.addAll(arenasNames);
                    break;
                case "addcoins":
                case "removecoins":
                    List<String> usersNames = userCoinsManager.getUsers().stream()
                            .map(userCoinsDbModel -> Bukkit.getOfflinePlayer(userCoinsDbModel.getUuid()).getName())
                            .collect(Collectors.toList());

                    completions.addAll(usersNames);
                    break;
            }
        }

        return completions;
    }
}

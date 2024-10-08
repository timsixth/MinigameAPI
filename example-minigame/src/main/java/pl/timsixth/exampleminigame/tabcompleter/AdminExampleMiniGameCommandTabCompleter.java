package pl.timsixth.exampleminigame.tabcompleter;

import org.bukkit.Bukkit;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.module.command.ParentCommand;
import pl.timsixth.minigameapi.api.module.command.tabcompleter.BaseTabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminExampleMiniGameCommandTabCompleter extends BaseTabCompleter {

    public AdminExampleMiniGameCommandTabCompleter(ParentCommand parentCommand, ArenaManager arenaManager, UserCoinsManager userCoinsManager) {
        super(parentCommand);

        this.addConditions((sender, args) -> {
            List<String> completions = new ArrayList<>();

            if (args.length == 1) {
                completions.addAll(Arrays.asList("create", "setspawn", "setlobby", "setgamelobby", "addcoins", "removecoins"));
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
        });
    }
}

package pl.timsixth.exampleminigame.tabcompleter;

import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.module.command.ParentCommand;
import pl.timsixth.minigameapi.api.module.command.tabcompleter.BaseTabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExampleMiniGameCommandTabCompleter extends BaseTabCompleter {

    public ExampleMiniGameCommandTabCompleter(ParentCommand parentCommand, ArenaManager arenaManager) {
        super(parentCommand);

        this.addConditions((sender, args) -> {
            List<String> completions = new ArrayList<>();

            if (args.length == 1) {
                completions.addAll(Arrays.asList("join", "leave", "list", "stats"));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                List<String> arenasNames = arenaManager.getArenas().stream()
                        .map(Arena::getName)
                        .collect(Collectors.toList());

                completions.addAll(arenasNames);
            }

            return completions;
        });
    }
}

package pl.timsixth.minigameapi.tabcompleter;

import pl.timsixth.minigameapi.addon.manager.AddonManager;
import pl.timsixth.minigameapi.addon.model.Addon;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.command.tabcompleter.BaseTabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MiniGameAPITabCompleter extends BaseTabCompleter {

    public MiniGameAPITabCompleter(ParentCommand parentCommand, AddonManager addonManager) {
        super(parentCommand);

        this.addConditions((sender, args) -> {
            List<String> completions = new ArrayList<>();
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("addon")) {
                    completions.addAll(Arrays.asList("list", "install", "enable", "disable", "update"));
                }
            } else if (args.length == 3) {
                switch (args[1].toLowerCase()) {
                    case "disable":
                    case "enable":
                    case "update":
                        List<String> addonsNames = addonManager.getAddons().stream()
                                .map(Addon::getName)
                                .collect(Collectors.toList());

                        completions.addAll(addonsNames);
                        break;
                }
            } else if (args.length == 4) {
                if (args[1].equalsIgnoreCase("install")) {
                    completions.add("latest");
                }
            }
            return completions;
        });
    }
}

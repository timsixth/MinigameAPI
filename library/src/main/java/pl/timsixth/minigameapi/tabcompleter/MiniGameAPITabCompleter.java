package pl.timsixth.minigameapi.tabcompleter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.timsixth.minigameapi.api.addon.manager.AddonManager;
import pl.timsixth.minigameapi.api.addon.model.Addon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MiniGameAPITabCompleter implements TabCompleter {

    private final AddonManager addonManager;

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("addon");
        } else if (args.length == 2) {
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
    }
}

package pl.timsixth.minigameapi.tabcompleter;

import org.bukkit.command.CommandSender;
import pl.timsixth.minigameapi.api.addon.manager.AddonManager;
import pl.timsixth.minigameapi.api.addon.model.Addon;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.command.tabcompleter.Argument;
import pl.timsixth.minigameapi.api.command.tabcompleter.BaseTabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MiniGameAPITabCompleter extends BaseTabCompleter {

    private final AddonManager addonManager;

    public MiniGameAPITabCompleter(ParentCommand parentCommand, AddonManager addonManager) {
        super(parentCommand);
        this.addonManager = addonManager;
    }

    @Override
    protected void onTabComplete(CommandSender sender, String[] args) {
        List<String> addonsNames = addonManager.getAddons().stream()
                .map(Addon::getName)
                .collect(Collectors.toList());

        this.addArgument(new Argument(2,
                Arrays.asList("list", "install", "enable", "disable", "update"), 0, "addon"));
        this.addArgument(new Argument(3, addonsNames, 1, Arrays.asList("disable", "enable", "update")));
        this.addArgument(new Argument(4, "latest", 1, "install"));
    }
}

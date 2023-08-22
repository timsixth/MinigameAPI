package pl.timsixth.thetag.command.subcommand.thetagadmin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.thetag.config.ConfigFile;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Collections;
@RequiredArgsConstructor
public class ReloadSubCommand implements SubCommand {

    private final ConfigFile configFile;
    private final MenuManager menuManager;
    private final Messages messages;
    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        configFile.reloadFiles(Collections.singletonList(menuManager));
        PlayerUtil.sendMessage(player, messages.getReloadedFiles());

        return false;
    }

    @Override
    public String getName() {
        return "reload";
    }
}

package pl.timsixth.thetag.command.subcommand.thetagadmin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.util.PlayerUtil;

@RequiredArgsConstructor
public class SetLobbySubCommand implements SubCommand {

    private final Settings settings;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        settings.setLobbyLocation(player.getLocation());
        PlayerUtil.sendMessage(player, messages.getSetLobbySpawn());

        return false;
    }

    @Override
    public String getName() {
        return "setlobby";
    }
}

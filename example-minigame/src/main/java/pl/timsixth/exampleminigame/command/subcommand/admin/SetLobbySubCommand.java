package pl.timsixth.exampleminigame.command.subcommand.admin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.minigameapi.api.module.command.SubCommand;

@RequiredArgsConstructor
public class SetLobbySubCommand implements SubCommand {

    private final Settings settings;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        settings.setLobbyLocation(player.getLocation());
        player.sendMessage(messages.getSetLobbySpawn());

        return false;
    }

    @Override
    public String getName() {
        return "setlobby";
    }
}

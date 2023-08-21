package pl.timsixth.thetag.command.subcommand.thetag;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.ArenaFileModel;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.minigameapi.api.util.ChatUtil;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ListSubCommand extends SubCommand {

    private final ArenaManager<ArenaFileModel> arenaManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        List<ArenaFileModel> arenas = arenaManager.getArenas();
        if (arenas.isEmpty()) {
            PlayerUtil.sendMessage(player, messages.getEmptyArenaList());
            return true;
        }
        PlayerUtil.sendMessage(player, messages.getArenaList());
        List<String> arenasNames = arenas.stream()
                .map(Arena::getName)
                .collect(Collectors.toList());

        arenasNames.forEach(arenaName -> PlayerUtil.sendMessage(player, ChatUtil.chatColor(ChatUtil.chatColor("&e- " + arenaName))));

        return false;
    }

    @Override
    public String getName() {
        return "list";
    }
}

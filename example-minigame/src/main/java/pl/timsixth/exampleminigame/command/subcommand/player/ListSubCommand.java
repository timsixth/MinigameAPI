package pl.timsixth.exampleminigame.command.subcommand.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ListSubCommand implements SubCommand {

    private final ArenaManager arenaManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        List<Arena> arenas = arenaManager.getArenas();
        if (arenas.isEmpty()) {
            player.sendMessage(messages.getEmptyArenaList());
            return true;
        }

        Object option = arenas.get(0).options().getValueOrDefault("option", 1); //gets custom option
        Bukkit.getLogger().info(String.valueOf(option));

        player.sendMessage(messages.getArenaList());
        List<String> arenasNames = arenas.stream()
                .map(Arena::getName)
                .collect(Collectors.toList());

        arenasNames.forEach(arenaName -> player.sendMessage(ChatUtil.chatColor(ChatUtil.chatColor("&e- " + arenaName))));

        return false;
    }

    @Override
    public String getName() {
        return "list";
    }
}

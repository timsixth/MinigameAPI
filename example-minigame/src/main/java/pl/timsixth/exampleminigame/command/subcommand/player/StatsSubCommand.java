package pl.timsixth.exampleminigame.command.subcommand.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.module.command.SubCommand;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.util.Optional;
@RequiredArgsConstructor
public class StatsSubCommand implements SubCommand {

    private final UserCoinsManager userCoinsManager;
    private final Messages messages;
    private final UserStatsManager userStatsManager;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Optional<UserCoins> userCoinsDbModelOptional = userCoinsManager.getUserByUuid(player.getUniqueId());
        if (!userCoinsDbModelOptional.isPresent()) return true;

        UserCoins userCoinsDbModel = userCoinsDbModelOptional.get();

        for (String playerStat : messages.getPlayerStats()) {
            player.sendMessage(ChatUtil.chatColor(playerStat
                    .replace("{PLAYER_DEFEATS}", String.valueOf(userStatsManager.getTotalDefeats(player.getUniqueId())))
                    .replace("{PLAYER_WINS}", String.valueOf(userStatsManager.getTotalWins(player.getUniqueId())))
                    .replace("{PLAYER_GAMES_PLAYED}", String.valueOf(userStatsManager.getTotalGamesPlayed(player.getUniqueId())))
                    .replace("{PLAYER_COINS}", String.valueOf(userCoinsDbModel.getCoins()))
            ));
        }

        return false;
    }

    @Override
    public String getName() {
        return "stats";
    }
}

package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.state.GameState;
import pl.timsixth.minigameapi.game.user.UserGame;
import pl.timsixth.minigameapi.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.stats.model.DefaultUserStats;
import pl.timsixth.minigameapi.stats.model.UserStatsDbModel;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.cosmetics.CosmeticCategory;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.util.ItemUtil;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;

@RequiredArgsConstructor
public class WinGameState implements GameState {

    private final Game game;
    private final TheTagPlugin theTagPlugin;
    private final Messages messages;
    private final UserStatsManager<UserStatsDbModel> statisticsManager;
    private final Settings settings;
    private final GameLogic gameLogic;

    @Override
    public void run() {
        for (UserGame playingUser : game.getPlayingUsers()) {
            playingUser.setPlaying(false);
            Player player = playingUser.toPlayer();

            PlayerUtil.sendMessage(player,messages.getPlayerWon().replace("{NICK}", player.getName()));

            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            player.teleport(settings.getLobbyLocation());
            ItemUtil.clearPlayerInventory(player);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            Optional<UserStatsDbModel> userStatsOptional = statisticsManager.getUser(player.getUniqueId(), game.getArena().getName());
            UserStatsDbModel userStats;
            if (!userStatsOptional.isPresent()) {
                userStats = new DefaultUserStats(player.getUniqueId(), player.getName(), game.getArena().getName());
                statisticsManager.addNewUser(userStats);

            } else {
                userStats = userStatsOptional.get();
            }
            userStats.addWin();

            UserCoinsManager<UserCoinsDbModel> userCoinsManager = theTagPlugin.getUserCoinsManager();

            Optional<UserCoinsDbModel> coinsDbModelOptional = userCoinsManager.getUserByUuid(player.getUniqueId());
            if (!coinsDbModelOptional.isPresent()) return;

            UserCoinsDbModel userCoinsDbModel = coinsDbModelOptional.get();

            userCoinsDbModel.addCoins(settings.getCostOfWin());
            gameLogic.showCosmetics(player, null,CosmeticCategory.WIN.name());
        }

        theTagPlugin.getGameManager().gameRestart(game);
    }
}

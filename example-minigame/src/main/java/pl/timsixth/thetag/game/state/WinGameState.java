package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.event.PlayerWinGameEvent;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.model.UserStats;
import pl.timsixth.minigameapi.api.stats.model.UserStatsImpl;
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
    private final UserStatsManager statisticsManager;
    private final Settings settings;
    private final GameLogic gameLogic;

    @Override
    public void run() {
        for (UserGame playingUser : game.getPlayingUsers()) {
            playingUser.setPlaying(false);
            Player player = playingUser.toPlayer();

            PlayerUtil.sendMessage(player, messages.getPlayerWon().replace("{NICK}", player.getName()));

            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            player.teleport(settings.getLobbyLocation());
            ItemUtil.clearPlayerInventory(player);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            Optional<UserStats> userStatsOptional = statisticsManager.getUser(player.getUniqueId(), game.getArena().getName());
            UserStats userStats;
            if (!userStatsOptional.isPresent()) {
                userStats = new UserStatsImpl(player.getUniqueId(), player.getName(), game.getArena().getName());
                statisticsManager.addNewUser(userStats);

            } else {
                userStats = userStatsOptional.get();
            }
            userStats.addWin();

            UserCoinsManager userCoinsManager = theTagPlugin.getUserCoinsManager();

            Optional<UserCoins> coinsDbModelOptional = userCoinsManager.getUserByUuid(player.getUniqueId());
            if (!coinsDbModelOptional.isPresent()) return;

            UserCoins userCoinsDbModel = coinsDbModelOptional.get();

            userCoinsDbModel.addCoins(settings.getCostOfWin());
            gameLogic.showCosmetics(player, null, CosmeticCategory.WIN.name());

            Bukkit.getPluginManager().callEvent(new PlayerWinGameEvent(player, game, (int) settings.getCostOfWin()));
        }

        theTagPlugin.getGameManager().gameRestart(game);
    }
}

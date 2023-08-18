package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.model.UserStatsDbModel;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.model.MyUserGame;
import pl.timsixth.thetag.manager.ScoreboardManager;
import pl.timsixth.thetag.util.ItemUtil;

import java.util.Optional;

import static pl.timsixth.thetag.util.ItemUtil.getHelmet;

@RequiredArgsConstructor
public class DrawingTheTagGameState implements GameState {

    private final GameLogic gameLogic;
    private final Game game;
    private final TheTagPlugin theTagPlugin;
    private final Settings settings;
    private final Messages messages;
    private final ScoreboardManager scoreboardManager;
    private final UserStatsManager<UserStatsDbModel> statisticsManager;

    @Override
    public void run() {
        MyUserGame myUserGame = gameLogic.randomUser(game);

        myUserGame.setTag(true);

        for (UserGame playingUser : game.getPlayingUsers()) {
            ItemUtil.clearPlayerInventory(playingUser.toPlayer());
            playingUser.setPlaying(true);

            if (playingUser.equals(myUserGame)) {
                Player player = playingUser.toPlayer();
                player.getInventory().setHelmet(getHelmet(Color.RED));
                continue;
            }
            Player player = playingUser.toPlayer();
            player.setHealth(20.0);
            player.setFoodLevel(20);
            player.getInventory().setHelmet(getHelmet(Color.GREEN));
        }

        if (game.getRounds() == 1) {
            for (UserGame playingUser : game.getPlayingUsers()) {
                Player player = playingUser.toPlayer();

                Optional<Location> locationOptional = game.getArena().getLocation("spawn");
                if (!locationOptional.isPresent()) return;

                player.teleport(locationOptional.get());

                player.sendTitle(messages.getGameStarted(), "", 10, 70, 20);
                player.setGameMode(GameMode.SURVIVAL);
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            }
        } else {
            for (UserGame playingUser : game.getPlayingUsers()) {
                Player player = playingUser.toPlayer();

                player.sendTitle(messages.getNextRoundStarted(), "", 10, 70, 20);
            }
        }

        if (game.getPlayingUsers().size() == 2) {
            game.setState(new FinalGameState(game, theTagPlugin, messages, settings, scoreboardManager, statisticsManager,gameLogic));
            return;
        }

        game.setState(new PlayingGameState(theTagPlugin, settings, messages, game, scoreboardManager, statisticsManager, gameLogic));
    }

}

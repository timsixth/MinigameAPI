package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.model.MyUserGame;
import pl.timsixth.thetag.manager.ScoreboardManager;
import pl.timsixth.thetag.task.StartFinalCountDown;

@RequiredArgsConstructor
public class FinalGameState implements GameState {

    private final Game game;
    private final TheTagPlugin theTagPlugin;
    private final Messages messages;
    private final Settings settings;
    private final ScoreboardManager scoreboardManager;
    private final UserStatsManager statisticsManager;
    private final GameLogic gameLogic;

    @Override
    public void run() {
        for (UserGame playingUser : game.getPlayingUsers()) {
            MyUserGame myUserGame = (MyUserGame) playingUser;

            Player player = myUserGame.toPlayer();

            int finalTimer = settings.getFinalTimer();

            if (settings.isUsePotionEffectsInFinal()) {
                if (myUserGame.isTag()) {
                    settings.getTheTagEffects().forEach((potionEffectType, level) -> player.addPotionEffect(new PotionEffect(potionEffectType, finalTimer * 20, level)));
                } else {
                    settings.getNoTheTagEffects().forEach((potionEffectType, level) -> player.addPotionEffect(new PotionEffect(potionEffectType, finalTimer * 20, level)));
                }
            }
            new StartFinalCountDown(theTagPlugin, game, messages, settings, scoreboardManager, statisticsManager, gameLogic, finalTimer)
                    .runTaskTimer(theTagPlugin, 20L, 20L);
        }
    }
}

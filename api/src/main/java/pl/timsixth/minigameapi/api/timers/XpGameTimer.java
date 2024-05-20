package pl.timsixth.minigameapi.api.timers;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.user.UserGame;

public abstract class XpGameTimer extends AbstractGameTimer {

    public XpGameTimer(Game game, int minPlayers, int time) {
        super(game, minPlayers, time);
    }

    @Override
    public void onStart() {
        for (UserGame user : game.getPlayingUsers()) {
            Player player = user.toPlayer();

            player.setLevel(time);
        }
    }

    @Override
    public void onCounting(int second) {
        for (UserGame user : game.getPlayingUsers()) {
            Player player = user.toPlayer();

            player.setLevel(second);
        }
    }
}

package pl.timsixth.minigameapi.api.timers;

import org.bukkit.scheduler.BukkitRunnable;
import pl.timsixth.minigameapi.api.game.Game;


public abstract class AbstractGameTimer extends BukkitRunnable implements GameTimer {

    protected final Game game;
    private final int minPlayers;
    protected final int time;

    private int seconds;

    protected AbstractGameTimer(Game game, int minPlayers, int time) {
        this.game = game;
        this.minPlayers = minPlayers;
        this.time = time;
        this.seconds = time;
    }

    @Override
    public void run() {
        if (seconds > 0) {
            if (seconds == time) onStart();

            onCounting(seconds);
            seconds--;
            if (seconds == 0) {
                onEnd();

                cancel();
                seconds = time;
            }
            if (game.getPlayingUsers().size() < minPlayers) {
                onCancel();

                cancel();
                seconds = time;
            }
        }
    }
}

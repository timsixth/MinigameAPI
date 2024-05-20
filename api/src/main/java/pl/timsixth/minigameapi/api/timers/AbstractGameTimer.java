package pl.timsixth.minigameapi.api.timers;

import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import pl.timsixth.minigameapi.api.game.Game;

@RequiredArgsConstructor
public abstract class AbstractGameTimer extends BukkitRunnable implements GameTimer {

    protected final Game game;
    private final int minPlayers;
    protected final int time;

    private int seconds = time;

    @Override
    public void run() {
        if (seconds > 0) {
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

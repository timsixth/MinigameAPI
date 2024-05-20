package pl.timsixth.minigameapi.api.timers;

import lombok.Setter;
import pl.timsixth.minigameapi.api.game.Game;

@Setter
public abstract class SimpleGameTimer extends AbstractGameTimer {

    public SimpleGameTimer(Game game, int minPlayers, int time) {
        super(game, minPlayers, time);
    }

    @Override
    public void onCounting(int second) {
        game.sendMessage("Game will starts in: " + second);
    }

}

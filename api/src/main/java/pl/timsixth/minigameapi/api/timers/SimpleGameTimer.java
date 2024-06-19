package pl.timsixth.minigameapi.api.timers;

import lombok.Setter;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.util.ChatUtil;

@Setter
public abstract class SimpleGameTimer extends AbstractGameTimer {

    public SimpleGameTimer(Game game, int minPlayers, int time) {
        super(game, minPlayers, time);
    }

    @Override
    public void onCounting(int second) {
        game.sendMessage(ChatUtil.chatColor("&7Game will starts in: &a" + second));
    }

}

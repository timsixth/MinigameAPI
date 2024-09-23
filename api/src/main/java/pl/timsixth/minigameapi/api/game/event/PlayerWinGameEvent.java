package pl.timsixth.minigameapi.api.game.event;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import pl.timsixth.minigameapi.api.game.Game;

/**
 * This event must be called to Boosters addon works correctly
 *
 * @deprecated use {@link GameWinEvent}
 */
@Getter
@Deprecated
public class PlayerWinGameEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Game game;
    private final double costOfWin;

    public PlayerWinGameEvent(Player who, Game game, double costOfWin) {
        super(who);
        this.game = game;
        this.costOfWin = costOfWin;
    }

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

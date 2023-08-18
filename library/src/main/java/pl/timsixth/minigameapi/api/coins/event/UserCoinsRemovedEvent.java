package pl.timsixth.minigameapi.api.coins.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.timsixth.minigameapi.api.coins.UserCoins;

@RequiredArgsConstructor
@Getter
public class UserCoinsRemovedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    /**
     * User's account which coins has been added
     */
    private final UserCoins userCoins;
    /**
     * Amount of added coins
     */
    private final int coins;

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

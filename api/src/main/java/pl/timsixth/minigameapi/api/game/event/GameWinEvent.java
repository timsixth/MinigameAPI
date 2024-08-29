package pl.timsixth.minigameapi.api.game.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.team.Team;

import java.util.List;
import java.util.UUID;

/**
 * The events must be called when game has win game state
 * <p>
 * This event stores game, winnerTeam, losersTeams, winners and losers
 */
@RequiredArgsConstructor
@Getter
public class GameWinEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Game game;
    private final Team winnerTeam;
    private final List<Team> losersTeams;
    private final List<UUID> winners;
    private final List<UUID> losers;

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

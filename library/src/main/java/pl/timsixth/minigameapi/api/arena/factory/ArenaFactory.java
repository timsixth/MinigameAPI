package pl.timsixth.minigameapi.api.arena.factory;

import org.bukkit.Location;
import pl.timsixth.minigameapi.api.arena.Arena;

import java.util.Map;

/**
 * Creates correct instance of arena
 */
public interface ArenaFactory {
    /**
     * Creates arena
     *
     * @param name          arena's name
     * @param lobbyLocation arena's lobby location
     * @param locations     arena's list of locations
     * @return correct instance of arena
     */
    Arena createArena(String name, Location lobbyLocation, Map<String, Location> locations);
}

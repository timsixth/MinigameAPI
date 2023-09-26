package pl.timsixth.minigameapi.api.arena.manager;

import pl.timsixth.minigameapi.api.arena.Arena;

import java.util.List;
import java.util.Optional;

/**
 * The class which manage arena system
 */
public interface ArenaManager {
    /**
     * Gets arena by name
     *
     * @param name arena name
     * @return every class which implemented {@link Arena}
     */
    Optional<Arena> getArena(String name);

    /**
     * @return list of all arenas
     */
    List<Arena> getArenas();

    /**
     * @return random arena name
     */
    String randomArenaName();

    /**
     * Adds new arena
     *
     * @param type every class which implemented {@link Arena}
     */
    void addArena(Arena type);

    /**
     * Removes arena
     *
     * @param type every class which implemented {@link Arena}
     */
    void removeArena(Arena type);
}

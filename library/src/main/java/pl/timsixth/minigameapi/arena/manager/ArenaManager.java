package pl.timsixth.minigameapi.arena.manager;

import pl.timsixth.minigameapi.arena.Arena;

import java.util.List;
import java.util.Optional;

/**
 * The class which manage arena system
 *
 * @param <T> every class which implemented {@link Arena}
 */
public interface ArenaManager<T extends Arena> {
    /**
     * Gets arena by name
     *
     * @param name arena name
     * @return every class which implemented {@link Arena}
     */
    Optional<T> getArena(String name);

    /**
     * @return list of all arenas
     */
    List<T> getArenas();

    /**
     * @return random arena name
     */
    String randomArenaName();

    /**
     * Adds new arena
     *
     * @param type every class which implemented {@link Arena}
     */
    void addArena(T type);

    /**
     * Removes arena
     *
     * @param type every class which implemented {@link Arena}
     */
    void removeArena(T type);
}

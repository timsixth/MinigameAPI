package pl.timsixth.minigameapi.arena.manager;

import pl.timsixth.minigameapi.arena.Arena;

import java.util.Optional;
import java.util.List;

public interface ArenaManager<T extends Arena> {

    Optional<T> getArena(String name);

    List<T> getArenas();

    String randomArenaName();

    void addArena(T type);

    void removeArena(T type);
}

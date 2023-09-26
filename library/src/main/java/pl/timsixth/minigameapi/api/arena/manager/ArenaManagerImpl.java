package pl.timsixth.minigameapi.api.arena.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.loader.Loader;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * The implementation of {@link ArenaManager}
 *
 * Every manager which works on loaded data must have injected loader
 */
@RequiredArgsConstructor
public class ArenaManagerImpl implements ArenaManager {

    private final Loader<Arena> arenaFileLoader;

    @Override
    public Optional<Arena> getArena(String name) {
        return arenaFileLoader.getData()
                .stream()
                .filter(arena -> arena.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public List<Arena> getArenas() {
        return arenaFileLoader.getData();
    }

    private List<String> getAreasNames() {
        return arenaFileLoader.getData().stream()
                .map(Arena::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String randomArenaName() {
        List<String> areasNames = getAreasNames();
        if (areasNames.isEmpty()) return "";
        return areasNames.get(ThreadLocalRandom.current().nextInt(areasNames.size()));
    }

    /**
     * Adds new arena to list and save to file
     *
     * @param type every class which implemented {@link Arena}
     */
    @Override
    public void addArena(Arena type) {
        this.arenaFileLoader.addObject(type);
        type.save();
    }

    /**
     * Removes new arena to list and deletes from file
     *
     * @param type every class which implemented {@link Arena}
     */
    @Override
    public void removeArena(Arena type) {
        this.arenaFileLoader.removeObject(type);
        type.delete();
    }

}

package pl.timsixth.minigameapi.api.arena.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.arena.ArenaFileModel;
import pl.timsixth.minigameapi.api.arena.loader.ArenaFileLoader;

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
public class ArenaManagerImpl implements ArenaManager<ArenaFileModel> {

    private final ArenaFileLoader arenaFileLoader;

    @Override
    public Optional<ArenaFileModel> getArena(String name) {
        return arenaFileLoader.getData()
                .stream()
                .filter(arena -> arena.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public List<ArenaFileModel> getArenas() {
        return arenaFileLoader.getData();
    }

    private List<String> getAreasNames() {
        return arenaFileLoader.getData().stream()
                .map(ArenaFileModel::getName)
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
     * @param type every class which implemented {@link ArenaFileModel}
     */
    @Override
    public void addArena(ArenaFileModel type) {
        this.arenaFileLoader.addObject(type);
        type.save();
    }

    /**
     * Removes new arena to list and deletes from file
     *
     * @param type every class which implemented {@link ArenaFileModel}
     */
    @Override
    public void removeArena(ArenaFileModel type) {
        this.arenaFileLoader.removeObject(type);
        type.delete();
    }

}

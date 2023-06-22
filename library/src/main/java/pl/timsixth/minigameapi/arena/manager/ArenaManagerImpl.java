package pl.timsixth.minigameapi.arena.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.loader.ArenaFileLoader;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

    @Override
    public void addArena(ArenaFileModel type) {
        this.arenaFileLoader.addObject(type);
        type.save();
    }

    @Override
    public void removeArena(ArenaFileModel type) {
        this.arenaFileLoader.removeObject(type);
        type.delete();
    }

}

package pl.timsixth.minigameapi.api.arena.loader;

import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.ArenaImpl;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;

/**
 * @see AbstractFileLoader
 */
public class ArenaSingleFileLoader extends AbstractFileLoader<Arena> implements ArenaLoader {
    /**
     * Loads arena from arenas.yml file
     */
    @Override
    public void load() {
        load("arenas.yml", "arenas");
    }

    /**
     * Loads data from file
     *
     * @param fileName       file to load
     * @param primarySection main section in file, This section is important to loading
     */
    @Override
    public void load(String fileName, String primarySection) {
        loadFile(fileName, primarySection, (key, section) -> this.addObject(section.getObject(key, ArenaImpl.class)));
    }

}

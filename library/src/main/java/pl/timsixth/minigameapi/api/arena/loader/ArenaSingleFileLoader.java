package pl.timsixth.minigameapi.api.arena.loader;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.ArenaImpl;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;

import java.io.File;

/**
 * @see AbstractFileLoader
 */
public class ArenaSingleFileLoader extends AbstractArenaFileLoader {
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
        File file = new File(MiniGame.getInstance().getDataFolder(), fileName);

        loadFile(file, primarySection);
    }

    @Override
    protected Class<? extends Arena> getArenaClass() {
        return ArenaImpl.class;
    }
}

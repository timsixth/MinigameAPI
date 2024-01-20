package pl.timsixth.minigameapi.api.arena.loader;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.MultiFilesArena;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;

import java.io.File;
/**
 * @see AbstractFileLoader
 */
public class ArenaManyFilesLoader extends AbstractArenaFileLoader {
    /**
     * Loads arenas from many files
     */
    @Override
    public void load() {
        File file = new File(MiniGame.getInstance().getDataFolder(), "arenas");

        File[] files = file.listFiles();

        if (files == null) return;

        for (File childFile : files) {
            load(childFile.getAbsolutePath(), "arena");
        }
    }
    /**
     * Loads data from single arena file
     *
     * @param fileName       file to load
     * @param primarySection main section in file, This section is important to loading
     */
    @Override
    public void load(String fileName, String primarySection) {
        File file = new File(fileName);

        loadFile(file, primarySection);
    }

    @Override
    protected Class<? extends Arena> getArenaClass() {
        return MultiFilesArena.class;
    }
}

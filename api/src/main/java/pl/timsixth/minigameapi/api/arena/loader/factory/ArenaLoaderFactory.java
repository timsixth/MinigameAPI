package pl.timsixth.minigameapi.api.arena.loader.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.loader.ArenaManyFilesLoader;
import pl.timsixth.minigameapi.api.arena.loader.ArenaSingleFileLoader;
import pl.timsixth.minigameapi.api.configuration.ArenaSaveType;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;

public class ArenaLoaderFactory implements LoaderFactory<Arena> {
    @Override
    public Loader<Arena> createLoader() {
        ArenaSaveType arenaSaveType = MiniGame.getInstance().getPluginConfiguration().getArenaSaveType();

        if (arenaSaveType == ArenaSaveType.MANY_FILES) {
            return new ArenaManyFilesLoader();
        }

        return new ArenaSingleFileLoader();
    }
}

package pl.timsixth.minigameapi.api.arena.factory;

import org.bukkit.Location;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.ArenaImpl;
import pl.timsixth.minigameapi.api.arena.MultiFilesArena;
import pl.timsixth.minigameapi.api.configuration.ArenaSaveType;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link ArenaFactory}
 */
public class ArenaFactoryImpl implements ArenaFactory {

    @Override
    public Arena createArena(String name, Location lobbyLocation, Map<String, Location> locations) {
        ArenaSaveType arenaSaveType = MiniGame.getInstance().getPluginConfiguration().getArenaSaveType();

        if (arenaSaveType == ArenaSaveType.MANY_FILES) {
            return new MultiFilesArena(name, lobbyLocation, locations);
        }

        return new ArenaImpl(name, lobbyLocation, locations);
    }

    @Override
    public Arena createArena(String name, Location lobbyLocation) {
        return createArena(name, lobbyLocation, new HashMap<>());
    }
}

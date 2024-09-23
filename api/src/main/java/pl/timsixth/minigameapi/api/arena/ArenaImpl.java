package pl.timsixth.minigameapi.api.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import pl.timsixth.minigameapi.api.file.SingleYamlFileDao;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.storage.Dao;
import pl.timsixth.minigameapi.api.util.options.OptionsImpl;

import java.util.Map;

/**
 * Implementation of {@link Arena}
 *
 * @see SingleYamlFileDao
 * @see SingleFile
 */
@Getter
@Setter
@SingleFile(fileName = "arenas.yml", primarySection = "arenas")
@SerializableAs("ArenaImpl")
public class ArenaImpl extends AbstractYamlFileArena {

    public ArenaImpl(String name, Location lobbyLocation, Map<String, Location> locations) {
        super(name, lobbyLocation, locations);
    }

    /**
     * This method is important to bukkit serialization system
     *
     * @param args to bukkit deserialization
     * @return this object
     */
    @SuppressWarnings("unchecked")
    public static ArenaImpl deserialize(Map<String, Object> args) {
        ArenaImpl arena = new ArenaImpl((String) args.get("name"), (Location) args.get("lobbyLocation"), (Map<String, Location>) args.get("locations"));

        if (args.get("arenaOptions") != null) arena.setOptions((OptionsImpl) args.get("arenaOptions"));

        return arena;
    }

    @Override
    public Dao getDao() {
        return new SingleYamlFileDao(this);
    }

}

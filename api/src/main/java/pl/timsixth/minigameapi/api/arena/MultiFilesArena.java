package pl.timsixth.minigameapi.api.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import pl.timsixth.minigameapi.api.file.ManyYamlFileDao;
import pl.timsixth.minigameapi.api.file.annotaions.ManyFiles;
import pl.timsixth.minigameapi.api.storage.Dao;
import pl.timsixth.minigameapi.api.util.options.OptionsImpl;

import java.util.Map;

/**
 * Implementation of {@link Arena}
 *
 * @see ManyYamlFileDao
 * @see ManyFiles
 */
@Getter
@Setter
@ManyFiles(parentDirectory = "arenas", primarySection = "arena")
@SerializableAs("MultiFilesArena")
public class MultiFilesArena extends AbstractYamlFileArena {

    public MultiFilesArena(String name, Location lobbyLocation, Map<String, Location> locations) {
        super(name, lobbyLocation, locations);
    }

    @SuppressWarnings("unchecked")
    public static MultiFilesArena deserialize(Map<String, Object> args) {
        MultiFilesArena multiFilesArena = new MultiFilesArena((String) args.get("name"), (Location) args.get("lobbyLocation"),
                (Map<String, Location>) args.get("locations"));

        if (args.get("arenaOptions") != null) multiFilesArena.setOptions((OptionsImpl) args.get("arenaOptions"));

        return multiFilesArena;
    }

    @Override
    public Dao getDao() {
        return new ManyYamlFileDao(this);
    }
}

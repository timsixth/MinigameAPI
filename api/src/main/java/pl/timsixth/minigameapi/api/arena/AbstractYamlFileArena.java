package pl.timsixth.minigameapi.api.arena;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import pl.timsixth.minigameapi.api.file.FileModel;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractYamlFileArena extends AbstractArena implements FileModel, ConfigurationSerializable {

    public AbstractYamlFileArena(String name, Location lobbyLocation, Map<String, Location> locations) {
        super(name, lobbyLocation, locations);
    }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", getName());
        data.put("lobbyLocation", getLobbyLocation());
        data.put("locations", getLocations());
        if (getOptions() != null) data.put("arenaOptions", getOptions());

        return data;
    }
}

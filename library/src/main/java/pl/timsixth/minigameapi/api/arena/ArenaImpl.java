package pl.timsixth.minigameapi.api.arena;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import pl.timsixth.minigameapi.api.file.SingleFileModel;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of {@link Arena}
 *
 * @see SingleFileModel
 * @see SingleFile
 * @see ArenaFileModel
 */
@Getter
@Setter
@SingleFile(fileName = "arenas.yml", primarySection = "arenas")
public class ArenaImpl extends SingleFileModel implements ArenaFileModel {

    @IdSection
    private String name;
    private Location lobbyLocation;
    private Map<String, Location> locations;

    public ArenaImpl(String name, Location lobbyLocation, Map<String, Location> locations) {
        this.name = name;
        this.lobbyLocation = lobbyLocation;
        this.locations = locations;
        init();
    }

    @Override
    public void addLocation(String name, Location location) {
        this.locations.put(name, location);
    }

    @Override
    public void removeLocation(String name) {
        this.locations.remove(name);
    }

    @Override
    public Optional<Location> getLocation(String name) {
        return Optional.of(locations.get(name));
    }

    /**
     * This method is important to bukkit serialization system
     *
     * @return map to serialize
     */
    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", name);
        data.put("lobbyLocation", lobbyLocation);
        data.put("locations", locations);

        return data;
    }

    /**
     * This method is important to bukkit serialization system
     *
     * @param args to bukkit deserialization
     * @return this object
     */
    public static ArenaImpl deserialize(Map<String, Object> args) {
        return new ArenaImpl((String) args.get("name"), (Location) args.get("lobbyLocation"), (Map<String, Location>) args.get("locations"));
    }
}

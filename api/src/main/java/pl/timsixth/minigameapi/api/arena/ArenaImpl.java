package pl.timsixth.minigameapi.api.arena;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.file.SingleFileModel;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.util.options.Options;
import pl.timsixth.minigameapi.api.util.options.OptionsImpl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of {@link Arena}
 *
 * @see SingleFileModel
 * @see SingleFile
 */
@Getter
@Setter
@SingleFile(fileName = "arenas.yml", primarySection = "arenas")
public class ArenaImpl extends SingleFileModel implements Arena, FileModel {

    @IdSection
    private String name;
    private Location lobbyLocation;
    private Map<String, Location> locations;
    private Options options;

    public ArenaImpl(String name, Location lobbyLocation, Map<String, Location> locations) {
        this.name = name;
        this.lobbyLocation = lobbyLocation;
        this.locations = locations;
        this.options = new OptionsImpl();
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
        return Optional.ofNullable(locations.get(name));
    }

    @Override
    public Options arenaOptions() {
        return options;
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
        if (options != null) data.put("arenaOptions", options);

        return data;
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

        if (args.get("arenaOptions") != null) arena.options = (OptionsImpl) args.get("arenaOptions");

        return arena;
    }

    @Override
    public Options options() {
        return options;
    }

    @Override
    public Options getOptionsOrCreate() {
        if (options == null) options = new OptionsImpl();

        return options;
    }
}

package pl.timsixth.minigameapi.api.arena;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import pl.timsixth.minigameapi.api.file.ManyFilesModel;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.file.annotaions.ManyFiles;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
/**
 * Implementation of {@link Arena}
 *
 * @see ManyFilesModel
 * @see ManyFiles
 */
@Getter
@Setter
@ManyFiles(parentDirectory = "arenas", primarySection = "arena")
public class MultiFilesArena extends ManyFilesModel implements Arena {

    @IdSection
    private String name;
    private Location lobbyLocation;
    private Map<String, Location> locations;

    public MultiFilesArena(String name, Location lobbyLocation, Map<String, Location> locations) {
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

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", name);
        data.put("lobbyLocation", lobbyLocation);
        data.put("locations", locations);

        return data;
    }

    public static MultiFilesArena deserialize(Map<String, Object> args) {
        return new MultiFilesArena((String) args.get("name"), (Location) args.get("lobbyLocation"), (Map<String, Location>) args.get("locations"));
    }
}

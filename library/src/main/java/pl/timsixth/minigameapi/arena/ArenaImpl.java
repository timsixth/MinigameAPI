package pl.timsixth.minigameapi.arena;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import pl.timsixth.minigameapi.file.SingleFileModel;
import pl.timsixth.minigameapi.file.annotaions.IdSection;
import pl.timsixth.minigameapi.file.annotaions.SingleFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
//@SerializableAs("T-MiniGameApiArena")
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

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", name);
        data.put("lobbyLocation", lobbyLocation);
        data.put("locations", locations);

        return data;
    }


    public static ArenaImpl deserialize(Map<String, Object> args) {
        return new ArenaImpl((String) args.get("name"),(Location) args.get("lobbyLocation"), (Map<String, Location>) args.get("locations"));
    }
}

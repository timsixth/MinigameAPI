package pl.timsixth.minigameapi.api.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import pl.timsixth.minigameapi.api.model.annotations.Id;
import pl.timsixth.minigameapi.api.util.options.Options;
import pl.timsixth.minigameapi.api.util.options.OptionsImpl;

import java.util.Map;
import java.util.Optional;

/**
 * Template method for {@link Arena}
 */
@Getter
@Setter
public abstract class AbstractArena implements Arena {

    @Id
    private String name;
    private Location lobbyLocation;
    private Map<String, Location> locations;
    private Options options;

    public AbstractArena(String name, Location lobbyLocation, Map<String, Location> locations) {
        this.name = name;
        this.lobbyLocation = lobbyLocation;
        this.locations = locations;
        this.options = new OptionsImpl();
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
    public Options options() {
        return options;
    }

    @Override
    public Options getOptionsOrCreate() {
        if (options == null) options = new OptionsImpl();

        return options;
    }
}

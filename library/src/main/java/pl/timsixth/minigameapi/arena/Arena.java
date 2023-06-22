package pl.timsixth.minigameapi.arena;

import org.bukkit.Location;

import java.util.Map;
import java.util.Optional;

public interface Arena {

    String getName();
    Location getLobbyLocation();
    Map<String, Location> getLocations();
    void setName(String name);
    void setLobbyLocation(Location location);
    void addLocation(String name,Location location);
    void removeLocation(String name);
    Optional<Location> getLocation(String name);
}

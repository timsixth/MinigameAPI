package pl.timsixth.minigameapi.arena;

import org.bukkit.Location;

import java.util.Map;
import java.util.Optional;

/**
 * Represents every arena
 */
public interface Arena {
    /**
     * @return arena name
     */
    String getName();

    /**
     * @return lobby location
     */
    Location getLobbyLocation();

    /**
     * @return map of locations in arena
     */
    Map<String, Location> getLocations();

    /**
     * Sets new name to arena
     *
     * @param name new name
     */
    void setName(String name);

    /**
     * Sets new lobby location
     *
     * @param location new location
     */
    void setLobbyLocation(Location location);

    /**
     * Adds location to Map
     *
     * @param name     location id
     * @param location location to add
     */
    void addLocation(String name, Location location);

    /**
     * Removes location by name
     *
     * @param name location id
     */
    void removeLocation(String name);

    /**
     * Gets location by name
     *
     * @param name location id
     * @return Optional of location
     */
    Optional<Location> getLocation(String name);
}

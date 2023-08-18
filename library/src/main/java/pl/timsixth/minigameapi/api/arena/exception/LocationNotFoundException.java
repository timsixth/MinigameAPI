package pl.timsixth.minigameapi.api.arena.exception;

/**
 * Throws when can not find location
 */
public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(String name) {
        super("Location with " + name + "not found");
    }
}

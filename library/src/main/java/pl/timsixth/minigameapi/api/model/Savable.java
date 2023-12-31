package pl.timsixth.minigameapi.api.model;

/**
 * Represents every model which can be saved
 */
public interface Savable {
    /**
     * Saves new model to database or file
     *
     * @return saved model
     */
    Object save();
}

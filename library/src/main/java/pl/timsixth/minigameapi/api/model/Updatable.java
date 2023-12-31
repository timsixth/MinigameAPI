package pl.timsixth.minigameapi.api.model;

/**
 * Represents every model which can be updated
 */
public interface Updatable {
    /**
     * Updates new model to database or file
     *
     * @return updated model
     */
    Object update();
}

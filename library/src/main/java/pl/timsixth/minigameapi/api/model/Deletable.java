package pl.timsixth.minigameapi.api.model;

/**
 * Represents every model which can be deleted
 */
public interface Deletable {
    /**
     * Deletes model from file or table
     *
     * @return true operation is succeeded otherwise false
     */
    boolean delete();
}

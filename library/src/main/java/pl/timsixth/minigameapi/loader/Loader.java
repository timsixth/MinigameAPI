package pl.timsixth.minigameapi.loader;

import pl.timsixth.minigameapi.model.Model;

import java.util.List;

/**
 * Loads data
 *
 * @param <T> every class which implemented {@link Model}
 */
public interface Loader<T extends Model> {
    /**
     * Loads data from database or file
     */
    void load();

    /**
     * Gets loaded data
     *
     * @return loaded data
     */
    List<T> getData();

    /**
     * Unloads data
     */
    void unload();

    /**
     * Adds new object to loaded data
     *
     * @param type object to add
     */
    void addObject(T type);

    /**
     * Removes object from loaded data
     *
     * @param type object to remove
     */
    void removeObject(T type);
}

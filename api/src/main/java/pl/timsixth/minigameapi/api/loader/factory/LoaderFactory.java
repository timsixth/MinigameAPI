package pl.timsixth.minigameapi.api.loader.factory;

import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Creates loader instance
 *
 * @param <T> class which implemented {@link Model}
 */
public interface LoaderFactory<T extends Model> {
    /**
     * Creates loader
     *
     * @return loader instance
     */
    Loader<T> createLoader();
}

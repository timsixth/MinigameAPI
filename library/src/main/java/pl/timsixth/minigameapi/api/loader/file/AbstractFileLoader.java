package pl.timsixth.minigameapi.api.loader.file;

import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.loader.AbstractLoader;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Template method for {@link FileLoader}
 *
 * @param <T> every class which implemented {@link FileModel}
 *
 * @see Loader
 * @see Loaders
 */
public abstract class AbstractFileLoader<T extends Model> extends AbstractLoader<T> implements FileLoader<T> {
}

package pl.timsixth.minigameapi.loader.file;

import pl.timsixth.minigameapi.file.FileModel;
import pl.timsixth.minigameapi.loader.AbstractLoader;

/**
 * Template method for {@link FileLoader}
 *
 * @param <T> every class which implemented {@link FileModel}
 *
 * @see pl.timsixth.minigameapi.loader.Loader
 * @see pl.timsixth.minigameapi.loader.Loaders
 */
public abstract class AbstractFileLoader<T extends FileModel> extends AbstractLoader<T> implements FileLoader<T> {
}

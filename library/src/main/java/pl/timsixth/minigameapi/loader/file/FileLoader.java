package pl.timsixth.minigameapi.loader.file;

import pl.timsixth.minigameapi.file.FileModel;
import pl.timsixth.minigameapi.loader.Loader;

/**
 * Loader which loads data from file
 *
 * @param <T> every class which implemented {@link FileModel}
 *
 * @see pl.timsixth.minigameapi.loader.Loader
 * @see pl.timsixth.minigameapi.loader.Loaders
 */
public interface FileLoader<T extends FileModel> extends Loader<T> {
    /**
     * Loads data from file
     *
     * @param fileName       file to load
     * @param primarySection main section in file, This section is important to loading
     */
    void load(String fileName, String primarySection);
}

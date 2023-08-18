package pl.timsixth.minigameapi.api.loader.file;

import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.loader.Loader;

/**
 * Loader which loads data from file
 *
 * @param <T> every class which implemented {@link FileModel}
 *
 * @see Loader
 * @see Loaders
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

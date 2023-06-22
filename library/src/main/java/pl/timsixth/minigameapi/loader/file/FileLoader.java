package pl.timsixth.minigameapi.loader.file;

import pl.timsixth.minigameapi.file.FileModel;
import pl.timsixth.minigameapi.loader.Loader;

public interface FileLoader<T extends FileModel> extends Loader<T> {

    void load(String fileName, String primarySection);
}

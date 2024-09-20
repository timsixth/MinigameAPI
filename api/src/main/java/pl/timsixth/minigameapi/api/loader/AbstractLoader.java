package pl.timsixth.minigameapi.api.loader;

import pl.timsixth.minigameapi.api.loader.file.FileLoader;
import pl.timsixth.minigameapi.api.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Template method for {@link Loader}
 *
 * @param <T> every class which implemented {@link Model}
 *
 * @see Loaders
 * @see FileLoader
 */
public abstract class AbstractLoader<T extends Model> implements Loader<T> {

    private final List<T> objects = new ArrayList<>();

    @Override
    public List<T> getData() {
        return objects;
    }

    @Override
    public void unload() {
        objects.clear();
    }

    @Override
    public void removeObject(T type) {
        objects.remove(type);
    }

    @Override
    public void addObject(T type) {
        objects.add(type);
    }
}

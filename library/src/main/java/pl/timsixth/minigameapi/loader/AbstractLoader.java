package pl.timsixth.minigameapi.loader;

import pl.timsixth.minigameapi.model.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLoader<T extends Model> implements Loader<T>{

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

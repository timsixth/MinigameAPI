package pl.timsixth.minigameapi.loader;

import pl.timsixth.minigameapi.model.Model;

import java.util.List;

public interface Loader<T extends Model> {

    void load();

    List<T> getData();

    void unload();

    void addObject(T type);

    void removeObject(T type);
}

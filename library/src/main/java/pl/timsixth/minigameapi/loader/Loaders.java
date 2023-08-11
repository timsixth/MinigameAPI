package pl.timsixth.minigameapi.loader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.configuration.type.PluginConfiguration;
import pl.timsixth.minigameapi.loader.database.SqlDataBaseLoader;
import pl.timsixth.minigameapi.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is managing every loader in plugin.
 * Every loader must be registered in main class.
 */
@RequiredArgsConstructor
public final class Loaders {

    private final PluginConfiguration pluginConfiguration;

    @Getter
    private final Map<Loader<? extends Model>, Boolean> loaders = new HashMap<>();

    /**
     * Registers more than one loader
     *
     * @param loaders list of loaders
     */
    @SafeVarargs
    public final void registerLoaders(Loader<? extends Model>... loaders) {
        if (loaders.length == 0) throw new IllegalStateException("Can not register loaders because loaders is empty.");

        for (Loader<? extends Model> loader : loaders) {
            registerLoader(loader);
        }
    }

    /**
     * Unregisters single loader
     *
     * @param loader loader to unregister
     */
    public void unregisterLoader(Loader<? extends Model> loader) {
        this.loaders.remove(loader);
    }

    /**
     * Registers single loader
     *
     * @param loader loader to register
     */
    public void registerLoader(Loader<? extends Model> loader) {
        if (this.loaders.containsKey(loader))
            throw new IllegalStateException("The loader is already registered");

        this.loaders.put(loader, false);
    }

    /**
     * Unregisters all loaders
     */
    public void unregisterLoaders() {
        this.loaders.clear();
    }

    /**
     * Loads all loaders in plugin
     */
    public void loadAll() {
        for (Map.Entry<Loader<? extends Model>, Boolean> loaderAndStatus : this.loaders.entrySet()) {
            Loader<? extends Model> loader = loaderAndStatus.getKey();

            if (this.loaders.get(loader)) continue;

            if (loader instanceof SqlDataBaseLoader) {
                if (pluginConfiguration.isUseDataBase()) {
                    loader.load();
                }
            } else {
                loader.load();
            }
            this.loaders.replace(loader, true);
        }
    }

    /**
     * Loads more than one loader
     *
     * @param loaders loaders to load
     */
    @SafeVarargs
    public final void load(Loader<? extends Model>... loaders) {
        if (loaders.length == 0) throw new IllegalStateException("Can not register loaders because loaders is empty.");

        for (Loader<? extends Model> loader : loaders) {
            if (this.loaders.get(loader)) continue;

            loader.load();
            this.loaders.replace(loader, true);
        }
    }
    /**
     * Loads single loader
     *
     * @param loader loader to load
     */
    public void load(Loader<? extends Model> loader) {
        if (!this.loaders.containsKey(loader))
            throw new IllegalStateException("Can not load data from unregister loader");

        loader.load();
        this.loaders.replace(loader, true);
    }

}

package pl.timsixth.minigameapi.api.module.mongodb.core.loader;

import com.mongodb.client.MongoDatabase;
import org.bukkit.Bukkit;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.AbstractLoader;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.mongodb.MongoDbModule;

public abstract class AbstractMongoDbLoader<T extends Model> extends AbstractLoader<T> implements MongoDbLoader<T> {

    @Override
    public void load() {
        Bukkit.getScheduler().runTaskAsynchronously(MiniGame.getInstance(), () -> load(getCollectionNameWithPrefix()));
    }

    /**
     * Gets table name which loader will load data
     *
     * @return table name
     */
    protected abstract String getCollectionName();

    /**
     * Gets table name with minigame tables' prefix
     *
     * @return table name with minigame tables' prefix
     */
    protected String getCollectionNameWithPrefix() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + getCollectionName();
    }

    protected MongoDatabase getMongoDatabase() {
        return MongoDbModule.getInstance().getMongoDbConnector().getMongoDatabase();
    }
}

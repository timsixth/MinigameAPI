package pl.timsixth.minigameapi.api.cosmetics.user.loader;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.user.SQLDatabaseUserCosmeticsAdapter;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @see AbstractSqlDataBaseLoader
 * @deprecated use SQL module
 */
@Deprecated
@RequiredArgsConstructor
public class UserCosmeticsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserCosmetics> implements UserCosmeticsLoader {

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        List<UserCosmetics> userCosmeticsList = loadUuids();
    }

    /**
     * Load uuid from database
     *
     * @return list of {@link UserCosmetics}
     */
    private List<UserCosmetics> loadUuids() {
        throw new UnsupportedOperationException("use SQL module");
    }

    /**
     * Loads cosmetics
     *
     * @param uuid user uuid
     * @return map with his cosmetics
     */
    private Map<Cosmetic, Boolean> getCosmetics(UUID uuid) {
        throw new UnsupportedOperationException("use SQL module");
    }

    @Override
    protected String getTableName() {
        return SQLDatabaseUserCosmeticsAdapter.TABLE_NAME;
    }
}

package pl.timsixth.minigameapi.api.module.sql.cosmetics.loader;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.logging.MiniGameLogger;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.module.sql.core.loader.AbstractSqlDataBaseLoader;
import pl.timsixth.minigameapi.api.module.sql.cosmetics.SQLDatabaseUserCosmetics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserCosmeticsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserCosmetics> implements UserCosmeticsLoader {

    private final CosmeticsManager cosmeticsManager;

    public UserCosmeticsSQLDatabaseLoader(SQLDatabaseAdapter sqlDatabaseAdapter, CosmeticsManager cosmeticsManager) {
        super(sqlDatabaseAdapter);
        this.cosmeticsManager = cosmeticsManager;
    }

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        List<UserCosmetics> userCosmeticsList = loadUuids();

        userCosmeticsList.forEach(userCosmetics -> {
            userCosmetics.setCosmetics(getCosmetics(userCosmetics.getUuid()));
            getData().add(userCosmetics);
        });
    }

    /**
     * Load uuid from database
     *
     * @return list of {@link UserCosmetics}
     */
    private List<UserCosmetics> loadUuids() {
        List<UserCosmetics> userCosmeticsList = new ArrayList<>();

        try (ResultSet resultSet = sqlDatabaseAdapter.selectAll(getTableNameWithPrefix())) {
            while (resultSet.next()) {
                UserCosmetics userCosmetics = MiniGame.getUserCosmeticsFactory().createUserCosmetics(
                        UUID.fromString(resultSet.getString("uuid"))
                );

                userCosmeticsList.add(userCosmetics);
            }
        } catch (SQLException e) {
            MiniGameLogger.error(e.getMessage(), e);
        }

        return userCosmeticsList;
    }

    /**
     * Loads cosmetics
     *
     * @param uuid user uuid
     * @return map with his cosmetics
     */
    private Map<Cosmetic, Boolean> getCosmetics(UUID uuid) {
        Map<Cosmetic, Boolean> cosmeticAndStatus = new HashMap<>();

        try (ResultSet resultSet = sqlDatabaseAdapter.selectWhere(getTableNameWithPrefix(), "uuid = '" + uuid + "'", "cosmetic", "enabled")) {
            while (resultSet.next()) {
                Optional<Cosmetic> cosmeticOptional = cosmeticsManager.getCosmeticByName(resultSet.getString("cosmetic"));

                if (!cosmeticOptional.isPresent()) continue;

                cosmeticAndStatus.put(cosmeticOptional.get(), resultSet.getBoolean("enabled"));
            }
        } catch (SQLException e) {
            MiniGameLogger.error(e.getMessage(), e);
        }

        return cosmeticAndStatus;
    }

    @Override
    protected String getTableName() {
        return SQLDatabaseUserCosmetics.TABLE_NAME;
    }
}

package pl.timsixth.minigameapi.api.cosmetics.user.loader;

import lombok.RequiredArgsConstructor;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmeticsImpl;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @see AbstractSqlDataBaseLoader
 */
@RequiredArgsConstructor
public class UserCosmeticsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserCosmetics> implements UserCosmeticsLoader{

    private final CosmeticsManager cosmeticsManager;

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
        QueryBuilder queryBuilder = new QueryBuilder();
        List<UserCosmetics> userCosmeticsList = new ArrayList<>();

        String query = queryBuilder.selectAll(getTableNameWithPrefix()).build();

        try (ResultSet resultSet = currentSqlDataBase.getAsyncQuery().query(query)) {
            while (resultSet.next()) {
                UserCosmetics userCosmetics = new UserCosmeticsImpl(
                        UUID.fromString(resultSet.getString("uuid"))
                );

                userCosmeticsList.add(userCosmetics);
            }
        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
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
        QueryBuilder queryBuilder = new QueryBuilder();
        Map<Cosmetic, Boolean> cosmeticAndStatus = new HashMap<>();

        String query = queryBuilder.select(getTableNameWithPrefix(), "cosmetic", "enabled").where("uuid = '" + uuid.toString() + "'").build();

        try (ResultSet resultSet = currentSqlDataBase.getAsyncQuery().query(query)) {
            while (resultSet.next()) {
                Optional<Cosmetic> cosmeticOptional = cosmeticsManager.getCosmeticByName(resultSet.getString("cosmetic"));

                if (!cosmeticOptional.isPresent()) continue;

                cosmeticAndStatus.put(cosmeticOptional.get(), resultSet.getBoolean("enabled"));
            }
        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }

        return cosmeticAndStatus;
    }

    @Override
    protected String getTableName() {
        return UserCosmeticsImpl.TABLE_NAME;
    }
}

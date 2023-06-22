package pl.timsixth.minigameapi.cosmetics.user.loader;

import lombok.RequiredArgsConstructor;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.cosmetics.user.UserCosmeticsDbModel;
import pl.timsixth.minigameapi.cosmetics.user.UserCosmeticsImpl;
import pl.timsixth.minigameapi.loader.database.AbstractSqlDataBaseLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class UserCosmeticsLoader extends AbstractSqlDataBaseLoader<UserCosmeticsDbModel> {

    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
    private final String TABLE_NAME = MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_cosmetics";

    private final CosmeticsManager cosmeticsManager;

    @Override
    public void load() {
        load(TABLE_NAME);
    }

    @Override
    public void load(String tableName) {
        List<UserCosmeticsDbModel> userCosmeticsList = loadUuids();

        userCosmeticsList.forEach(userCosmetics -> {
            userCosmetics.setCosmetics(getCosmetics(userCosmetics.getUuid()));
            getData().add(userCosmetics);
        });
    }

    private List<UserCosmeticsDbModel> loadUuids() {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<UserCosmeticsDbModel> userCosmeticsList = new ArrayList<>();

        String query = queryBuilder.selectAll(TABLE_NAME).build();

        try (ResultSet resultSet = currentSqlDataBase.getAsyncQuery().query(query)) {
            while (resultSet.next()) {
                UserCosmeticsDbModel userCosmetics = new UserCosmeticsImpl(
                        UUID.fromString(resultSet.getString("uuid"))
                );

                userCosmeticsList.add(userCosmetics);
            }
        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
        return userCosmeticsList;
    }

    private Map<Cosmetic, Boolean> getCosmetics(UUID uuid) {
        QueryBuilder queryBuilder = new QueryBuilder();
        Map<Cosmetic, Boolean> cosmeticAndStatus = new HashMap<>();

        String query = queryBuilder.select(TABLE_NAME, "cosmetic", "enabled").where("uuid = '" + uuid.toString() + "'").build();

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

}

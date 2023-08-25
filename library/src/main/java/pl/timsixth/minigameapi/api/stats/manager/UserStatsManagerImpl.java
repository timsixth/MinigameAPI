package pl.timsixth.minigameapi.api.stats.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.loader.database.SqlDataBaseLoader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;
import pl.timsixth.minigameapi.api.stats.model.UserStatsDbModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link UserStatsManager}
 *
 * @see UserStats
 * @see AbstractUserStatsManager
 */
@RequiredArgsConstructor
public class UserStatsManagerImpl extends AbstractUserStatsManager<UserStatsDbModel> {

    private final SqlDataBaseLoader<UserStatsDbModel> userStatsSqlDataBaseLoader;

    @Override
    public Optional<UserStatsDbModel> getUser(UUID uuid, String arenaName) {
        return userStatsSqlDataBaseLoader.getData()
                .stream().filter(userStats -> userStats.getUuid().equals(uuid))
                .filter(userStats -> userStats.getArenaName().equalsIgnoreCase(arenaName))
                .findAny();
    }

    @Override
    public List<UserStatsDbModel> getAllStatsByUuid(UUID uuid) {
        return userStatsSqlDataBaseLoader.getData().stream()
                .filter(userStats -> userStats.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }

    @Override
    public void addNewUser(UserStatsDbModel userStats) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String sql = queryBuilder.insert(userStats.getTableName(), null, userStats.getUuid().toString(), userStats.getName(),
                userStats.getArenaName(), userStats.getWins(), userStats.getDefeats()).build();

        try {
            DatabasesApiPlugin.getApi().getCurrentSqlDataBase().getAsyncQuery().update(sql);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        userStatsSqlDataBaseLoader.addObject(userStats);
    }
}

package pl.timsixth.minigameapi.api.stats.manager;

import lombok.AccessLevel;
import lombok.Getter;
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
 * @param <T> every class which implemented {@link UserStatsDbModel}
 * @see UserStats
 * @see AbstractUserStatsManager
 */
@RequiredArgsConstructor
public class UserStatsManagerImpl<T extends UserStatsDbModel> extends AbstractUserStatsManager<T> {

    @Getter(AccessLevel.PROTECTED)
    private final SqlDataBaseLoader<T> userStatsSqlDataBaseLoader;

    @Override
    public Optional<T> getUser(UUID uuid, String arenaName) {
        return userStatsSqlDataBaseLoader.getData()
                .stream().filter(userStats -> userStats.getUuid().equals(uuid))
                .filter(userStats -> userStats.getArenaName().equalsIgnoreCase(arenaName))
                .findAny();
    }

    @Override
    public List<T> getAllStatsByUuid(UUID uuid) {
        return userStatsSqlDataBaseLoader.getData().stream()
                .filter(userStats -> userStats.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }

    @Override
    public void addNewUser(T userStats) {
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

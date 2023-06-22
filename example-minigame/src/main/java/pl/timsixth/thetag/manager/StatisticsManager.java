package pl.timsixth.thetag.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.loader.database.SqlDataBaseLoader;
import pl.timsixth.minigameapi.stats.AbstractStatistics;
import pl.timsixth.thetag.model.UserStats;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StatisticsManager extends AbstractStatistics<UserStats> {

    private final SqlDataBaseLoader<UserStats> userStatsSqlDataBaseLoader;

    @Override
    public Optional<UserStats> getUser(UUID uuid, String arenaName) {
        return userStatsSqlDataBaseLoader.getData()
                .stream().filter(userStats -> userStats.getUuid().equals(uuid))
                .filter(userStats -> userStats.getArenaName().equalsIgnoreCase(arenaName))
                .findAny();
    }

    @Override
    public List<UserStats> getAllStatsByUuid(UUID uuid) {
        return userStatsSqlDataBaseLoader.getData().stream()
                .filter(userStats -> userStats.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }

    public int getTotalWins(UUID uuid) {
        return getTotal(uuid, UserStats::getWins);
    }

    public int getTotalDefeats(UUID uuid) {
        return getTotal(uuid, UserStats::getDefeats);
    }

    public int getTotalGamesPlayed(UUID uuid) {
        return getTotal(uuid, UserStats::getGamesPlayed);
    }

    public void addNewUser(UserStats userStats) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String sql = queryBuilder.insert(userStats.getTableName(), null, userStats.getUuid().toString(), userStats.getName(),
                userStats.getArenaName(), userStats.getWins(), userStats.getDefeats(), userStats.getGamesPlayed()).build();

        try {
            DatabasesApiPlugin.getApi().getCurrentSqlDataBase().getAsyncQuery().update(sql);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        userStatsSqlDataBaseLoader.addObject(userStats);
    }
}

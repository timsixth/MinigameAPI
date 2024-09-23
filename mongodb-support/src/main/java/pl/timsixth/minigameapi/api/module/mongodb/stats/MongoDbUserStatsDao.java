package pl.timsixth.minigameapi.api.module.mongodb.stats;

import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.mongodb.core.dao.MongoDbDao;

public class MongoDbUserStatsDao extends MongoDbDao {

    private final MongoDbUserStats userStats;

    public MongoDbUserStatsDao(Model model) {
        super(model);

        this.userStats = (MongoDbUserStats) model;
    }

    @Override
    public Object update(Model model) {
        Document document = new Document();
        document.append("wins", userStats.getWins());
        document.append("defeats", userStats.getDefeats());

        Document updateQuery = new Document();
        updateQuery.append("$set", document);

        Document query = new Document();
        query.append("uuid", getId());
        query.append("arenaName", userStats.getArenaName());

        getCollection().updateOne(query, updateQuery);

        return model;
    }

    @Override
    public boolean delete(Model model) {
        Document query = new Document();
        query.append("uuid", userStats.getUuid().toString());
        query.append("arenaName", userStats.getArenaName());

        DeleteResult deleteResult = getCollection().deleteOne(query);

        return deleteResult.getDeletedCount() == 1;
    }
}

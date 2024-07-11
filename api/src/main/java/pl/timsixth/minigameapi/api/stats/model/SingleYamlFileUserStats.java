package pl.timsixth.minigameapi.api.stats.model;

import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.stats.dao.SingleYamlFileUserStatsDao;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

@SingleFile(fileName = "users_stats.yml", primarySection = "users")
public class SingleYamlFileUserStats extends AbstractUserStats implements FileModel {

    public SingleYamlFileUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        super(uuid, name, arenaName, wins, defeats);
    }

    @Override
    public Dao getDao() {
        return new SingleYamlFileUserStatsDao(this);
    }
}

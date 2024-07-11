package pl.timsixth.minigameapi.api.coins;

import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

public class SingleYamlUserCoins extends AbstractUserCoins implements FileModel {
    public SingleYamlUserCoins(UUID uuid, double coins) {
        super(uuid, coins);
    }


    @Override
    public Dao getDao() {
        return null;
    }
}

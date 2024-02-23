package pl.timsixth.minigameapi.api.cosmetics.user.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.cosmetics.user.SQLDatabaseUserCosmeticsAdapter;
import pl.timsixth.minigameapi.api.cosmetics.user.SingleFileUserCosmeticsAdapter;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.database.impl.DbModelImpl;
import pl.timsixth.minigameapi.api.file.impl.SingleFileModelImpl;

import java.util.UUID;

public class UserCosmeticsFactoryImpl implements UserCosmeticsFactory {
    @Override
    public UserCosmetics createUserCosmetics(UUID uuid) {
        if (MiniGame.getInstance().getPluginConfiguration().isUseDataBase())
            return new SQLDatabaseUserCosmeticsAdapter(new DbModelImpl(), uuid);

        return new SingleFileUserCosmeticsAdapter(new SingleFileModelImpl(), uuid);
    }
}

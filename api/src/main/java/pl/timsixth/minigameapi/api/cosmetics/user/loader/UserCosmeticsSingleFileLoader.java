package pl.timsixth.minigameapi.api.cosmetics.user.loader;

import pl.timsixth.minigameapi.api.cosmetics.user.SingleFileUserCosmeticsAdapter;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;

public class UserCosmeticsSingleFileLoader extends AbstractFileLoader<UserCosmetics> implements UserCosmeticsLoader {
    @Override
    public void load() {
        load("users_cosmetics.yml", "users_cosmetics");
    }

    @Override
    public void load(String fileName, String primarySection) {
        loadFile(fileName, primarySection, (uuid, section) ->
                this.addObject(section.getObject(uuid, SingleFileUserCosmeticsAdapter.class)));
    }
}

package pl.timsixth.minigameapi.api.coins.loader;

import pl.timsixth.minigameapi.api.coins.SingleFileUserCoinsAdapter;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;

public class UserCoinsSingleFileLoader extends AbstractFileLoader<UserCoins> implements UserCoinsLoader {
    @Override
    public void load() {
        load("users_coins.yml", "users");
    }

    @Override
    public void load(String fileName, String primarySection) {
        loadFile(fileName, primarySection, (key, section) ->
                this.addObject(section.getObject(key, SingleFileUserCoinsAdapter.class)));
    }

}

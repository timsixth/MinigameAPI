package pl.timsixth.minigameapi.api.coins;

import pl.timsixth.minigameapi.api.database.annoations.Table;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.UUID;
/**
 * Adapter for UserCoins model which is saving in SQL database
 *
 * @see Table
 * @see AbstractUserCoinsAdapter
 * @see UserCoins
 * @see AbstractModelAdapter
 */
@Table(name = "users_coins")
@Deprecated
public class SQLDatabaseUserCoinsAdapter extends AbstractUserCoinsAdapter {

    public static final String TABLE_NAME = "users_coins";

    public SQLDatabaseUserCoinsAdapter(InitializableModel model, UUID uuid, double coins) {
        super(model, uuid, coins);
        model.init(this);
    }
}

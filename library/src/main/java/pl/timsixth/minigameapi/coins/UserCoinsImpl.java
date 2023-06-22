package pl.timsixth.minigameapi.coins;

import lombok.Getter;
import lombok.ToString;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.database.AbstractDbModel;
import pl.timsixth.minigameapi.database.annoations.Id;

import java.util.UUID;

/**
 * Implementation of {@link UserCoins}
 *
 * @see AbstractDbModel
 * @see UserCoinsDbModel
 */
@Getter
@ToString
public class UserCoinsImpl extends AbstractDbModel implements UserCoinsDbModel {

    @Id
    private final UUID uuid;
    private int coins;

    public UserCoinsImpl(UUID uuid, double coins) {
        this.uuid = uuid;
        this.coins = (int) coins;
        init();
    }

    @Override
    public void setCoins(double coins) {
        this.coins = (int) coins;
        update();
    }

    @Override
    public void addCoins(double coins) {
        this.coins += (int) coins;
        update();
    }

    @Override
    public void removeCoins(double coins) {
        this.coins -= (int) coins;
        update();
    }

    @Override
    public double getCoins() {
        return coins;
    }

    @Override
    public boolean hasCoins(double coins) {
        return this.coins >= coins;
    }

    @Override
    public String getTableName() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_coins";
    }
}

package pl.timsixth.minigameapi.api.coins;

import lombok.Getter;
import lombok.ToString;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;

import java.util.UUID;

/**
 * Implementation of {@link UserCoins}
 *
 * @see AbstractDbModel
 */
@Getter
@ToString
public class UserCoinsImpl extends AbstractDbModel implements UserCoins {

    @Id
    private final UUID uuid;
    private double coins;

    public static final String TABLE_NAME = "users_coins";

    public UserCoinsImpl(UUID uuid, double coins) {
        this.uuid = uuid;
        this.coins = (int) coins;
        init();
    }

    @Override
    public void setCoins(double coins) {
        this.coins = coins;
        update();
    }

    @Override
    public void addCoins(double coins) {
        this.coins += coins;
        update();
    }

    @Override
    public void removeCoins(double coins) {
        this.coins -= coins;
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
        return TABLE_NAME;
    }
}

package pl.timsixth.minigameapi.user;

import lombok.Getter;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.database.annoations.Id;

import java.util.UUID;
@Getter
public class UserCoinsStub extends AbstractDbModelStub implements UserCoinsDbModel {

    @Id
    private final UUID uuid;
    private int coins;

    public UserCoinsStub(UUID uuid, double coins) {
        this.uuid = uuid;
        this.coins = (int) coins;
        init();
    }

    @Override
    public void setCoins(double coins) {
        this.coins = (int) coins;
    }

    @Override
    public void addCoins(double coins) {
        this.coins += (int) coins;
    }

    @Override
    public void removeCoins(double coins) {
        this.coins -= (int) coins;
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
        return "users_coins_test";
    }
}

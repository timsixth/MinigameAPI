package pl.timsixth.minigameapi.api.coins;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.event.UserCoinsReceivedEvent;
import pl.timsixth.minigameapi.api.coins.event.UserCoinsRemovedEvent;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;

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

        UserCoinsReceivedEvent userCoinsReceivedEvent = new UserCoinsReceivedEvent(this, (int) coins);

        Bukkit.getPluginManager().callEvent(userCoinsReceivedEvent);
    }

    @Override
    public void removeCoins(double coins) {
        this.coins -= (int) coins;
        update();

        UserCoinsRemovedEvent userCoinsRemovedEvent = new UserCoinsRemovedEvent(this, (int) coins);

        Bukkit.getPluginManager().callEvent(userCoinsRemovedEvent);
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
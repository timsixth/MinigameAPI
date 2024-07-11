package pl.timsixth.minigameapi.api.coins;

import lombok.Getter;
import pl.timsixth.minigameapi.api.model.annotations.Id;

import java.util.UUID;

@Getter
public abstract class AbstractUserCoins implements UserCoins {

    @Id
    private final UUID uuid;
    private double coins;

    public AbstractUserCoins(UUID uuid, double coins) {
        this.uuid = uuid;
        this.coins = coins;
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
}

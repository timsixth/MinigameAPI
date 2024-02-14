package pl.timsixth.minigameapi.api.coins;

import lombok.Getter;
import lombok.ToString;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.UUID;

/**
 * Represents every {@link UserCoins} model
 *
 * @see pl.timsixth.minigameapi.api.model.AbstractModelAdapter
 * @see InitializableModel
 */
@Getter
@ToString
public abstract class AbstractUserCoinsAdapter extends AbstractModelAdapter implements UserCoins {

    @Id
    @IdSection
    private final UUID uuid;
    private double coins;

    public AbstractUserCoinsAdapter(InitializableModel model, UUID uuid, double coins) {
        super(model);
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

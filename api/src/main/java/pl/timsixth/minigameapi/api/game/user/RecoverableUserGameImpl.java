package pl.timsixth.minigameapi.api.game.user;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.minigameapi.api.util.options.Options;
import pl.timsixth.minigameapi.api.util.options.OptionsImpl;

/**
 * Implementation of {@link RecoverableUserGame}
 */
@Getter
@ToString
public class RecoverableUserGameImpl extends UserGameImpl implements RecoverableUserGame {

    private final ItemStack[] contents;
    private final ItemStack[] armor;
    private final Location latestLocation;
    private final double health;
    private final float experience;
    private final int level;
    private final int foodLevel;
    private final int fireTicks;
    private Options options;

    public RecoverableUserGameImpl(Player player, int points) {
        super(player.getUniqueId(), points, false);
        this.contents = player.getInventory().getContents();
        this.armor = player.getInventory().getArmorContents();
        this.latestLocation = player.getLocation();
        this.health = player.getHealth();
        this.experience = player.getExp();
        this.level = player.getLevel();
        this.foodLevel = player.getFoodLevel();
        this.fireTicks = player.getFireTicks();
        this.options = new OptionsImpl();
    }

    @Override
    public Options options() {
        return options;
    }

    @Override
    public Options getOptionsOrCreate() {
        if (options == null) options = new OptionsImpl();

        return options;
    }

    @Override
    public void setPoints(int points) {
        throw new UnsupportedOperationException("State of RecoverableUserGameImpl can not be changed");
    }

    @Override
    public void setPlaying(boolean isPlaying) {
        throw new UnsupportedOperationException("State of RecoverableUserGameImpl can not be changed");
    }

    @Override
    public int addPoints(int points) {
        throw new UnsupportedOperationException("State of RecoverableUserGameImpl can not be changed");
    }

    @Override
    public int removePoints(int points) {
        throw new UnsupportedOperationException("State of RecoverableUserGameImpl can not be changed");
    }
}

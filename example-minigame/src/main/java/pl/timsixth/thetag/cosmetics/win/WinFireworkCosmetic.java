package pl.timsixth.thetag.cosmetics.win;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.thetag.util.ItemUtil;

public class WinFireworkCosmetic implements Cosmetic {

    @Override
    public String getName() {
        return "WIN_FIREWORK";
    }

    @Override
    public void show(Player player) {
        Location location = player.getLocation();
        double y = location.getY() + 2;

        location.setY(y);

        ItemUtil.createFirework(location, FireworkEffect.Type.CREEPER, Color.GREEN);
    }
}

package pl.timsixth.exampleminigame.cosmetics;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.util.ItemUtil;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;

/*
Sample cosmetic
 */
public class FireworkCosmetic implements Cosmetic {


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

package pl.timsixth.thetag.cosmetics.defeat;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;

public class DefeatLightningCosmetic implements Cosmetic {

    @Override
    public String getName() {
        return "DEFEAT_LIGHTNING";
    }

    @Override
    public void show(Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
    }
}

package pl.timsixth.exampleminigame.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;


@UtilityClass
public class ItemUtil {

    public static void createFirework(Location location, FireworkEffect.Type type, Color color) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(FireworkEffect
                .builder()
                .with(type)
                .withColor(color)
                .build()
        );
        firework.setFireworkMeta(meta);
    }
}

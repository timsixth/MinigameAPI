package pl.timsixth.thetag.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;


@UtilityClass
public class ItemUtil {

    public static ItemStack getHelmet(Color color) {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);

        itemStack.setItemMeta(leatherArmorMeta);

        return itemStack;
    }

    public static void clearPlayerInventory(Player player) {
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
    }

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

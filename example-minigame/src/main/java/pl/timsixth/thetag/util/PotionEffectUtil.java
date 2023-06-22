package pl.timsixth.thetag.util;

import lombok.experimental.UtilityClass;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class PotionEffectUtil {

    public static Map<PotionEffectType, Integer> getPotionEffects(List<String> potionEffectsAndLevels) {
        Map<PotionEffectType, Integer> potionEffects = new HashMap<>();

        potionEffectsAndLevels.forEach(potionEffectAndLevel -> {
            String[] split = potionEffectAndLevel.split(";");

            potionEffects.put(PotionEffectType.getByName(split[0]), Integer.parseInt(split[1]));
        });

        return potionEffects;
    }

}

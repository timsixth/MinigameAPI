package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.cosmetics.CosmeticCategory;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.model.MyUserGame;

import java.util.Optional;

import static pl.timsixth.thetag.util.ItemUtil.getHelmet;

@RequiredArgsConstructor
public class EntityDamageByEntityListener implements Listener {

    private final GameManager gameManager;
    private final Settings settings;
    private final GameLogic gameLogic;

    @EventHandler
    private void onEntityHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player entity = (Player) event.getEntity();

            Optional<Game> gameOptionalByDamager = gameManager.getGameByPlayer(damager);
            Optional<Game> gameOptionalByEntity = gameManager.getGameByPlayer(entity);

            if (!gameOptionalByDamager.isPresent() || !gameOptionalByEntity.isPresent()) {
                return;
            }

            Game game = gameOptionalByDamager.get();

            Optional<UserGame> userGameDamagerOptional = game.getUserGame(damager.getUniqueId());
            Optional<UserGame> userGameEntityOptional = game.getUserGame(entity.getUniqueId());

            if (!userGameDamagerOptional.isPresent()) return;
            if (!userGameEntityOptional.isPresent()) return;

            MyUserGame myUserGameDamager = (MyUserGame) userGameDamagerOptional.get();
            MyUserGame myUserGameEntity = (MyUserGame) userGameEntityOptional.get();

            entity.setHealth(20.0);

            gameLogic.showCosmetics(entity, damager, CosmeticCategory.HIT.name());
            gameLogic.showCosmetics(damager, entity, CosmeticCategory.HIT.name());

            if (myUserGameDamager.isTag()) {
                Player damagerPlayer = myUserGameDamager.toPlayer();
                Player entityPlayer = myUserGameEntity.toPlayer();

                myUserGameDamager.setTag(false);
                damagerPlayer.getInventory().setHelmet(getHelmet(Color.GREEN));

                myUserGameEntity.setTag(true);
                entityPlayer.getInventory().setHelmet(getHelmet(Color.RED));

                if (game.getPlayingUsers().size() == 2) {
                    int finalTimer = settings.getFinalTimer();

                    damagerPlayer.getActivePotionEffects().forEach(potionEffect -> damagerPlayer.removePotionEffect(potionEffect.getType()));
                    entityPlayer.getActivePotionEffects().forEach(potionEffect -> entityPlayer.removePotionEffect(potionEffect.getType()));

                    if (settings.isUsePotionEffectsInFinal()) {
                        settings.getNoTheTagEffects().forEach((potionEffectType, level) -> damagerPlayer.addPotionEffect(new PotionEffect(potionEffectType, finalTimer * 20, level)));
                        settings.getTheTagEffects().forEach((potionEffectType, level) -> entityPlayer.addPotionEffect(new PotionEffect(potionEffectType, finalTimer * 20, level)));
                    }
                }
            }
        }
    }
}

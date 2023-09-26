package pl.timsixth.thetag.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmeticsImpl;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;

public class BuyOrActiveCosmeticAction extends AbstractAction implements ClickAction {

    private final TheTagPlugin theTagPlugin = TheTagPlugin.getPlugin(TheTagPlugin.class);
    private final Messages messages = theTagPlugin.getMessages();

    public BuyOrActiveCosmeticAction() {
        super("BUY_OR_ACTIVE_COSMETIC");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        UserCoinsManager userCoinsManager = theTagPlugin.getUserCoinsManager();
        UserCosmeticsManager userCosmeticsManager = theTagPlugin.getUserCosmeticsManager();

        Optional<UserCosmetics> userCosmeticsOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());
        Optional<Cosmetic> cosmeticOptional = theTagPlugin.getCosmeticsManager().getCosmeticByName(getArgs().get(0));
        Optional<UserCoins> userCoinsOptional = userCoinsManager.getUserByUuid(player.getUniqueId());

        if (!cosmeticOptional.isPresent()) {
            event.setCancelled(true);
            return;
        }

        if (!userCoinsOptional.isPresent()) {
            event.setCancelled(true);
            return;
        }

        Cosmetic cosmetic = cosmeticOptional.get();
        UserCoins userCoinsDbModel = userCoinsOptional.get();

        if (userCosmeticsOptional.isPresent()) {
            UserCosmetics userCosmeticsDbModel = userCosmeticsOptional.get();

            if (userCosmeticsDbModel.hasCosmetic(cosmetic)) {
                if (userCosmeticsDbModel.isCosmeticEnable(cosmetic)) {
                    userCosmeticsDbModel.disableCosmetic(cosmetic);
                    PlayerUtil.sendMessage(player, messages.getDisabledCosmetic());
                } else {
                    String category = cosmetic.getName().split("_")[0];
                    if (userCosmeticsDbModel.getActiveCosmeticsByCategory(category).size() == 1) {
                        PlayerUtil.sendMessage(player, messages.getPleaseResetCosmetic());
                        event.setCancelled(true);
                        return;
                    }

                    userCosmeticsDbModel.enableCosmetic(cosmetic);
                    PlayerUtil.sendMessage(player, messages.getEnabledCosmetic());
                }
                event.setCancelled(true);
            } else {
                if (hasCoins(event, menuItem, player, userCoinsDbModel)) return;

                buyCosmetic(event, menuItem, player, userCoinsDbModel);
                userCosmeticsDbModel.addCosmetic(cosmetic);
            }
        } else {
            if (hasCoins(event, menuItem, player, userCoinsDbModel)) return;

            UserCosmetics userCosmeticsDbModel = new UserCosmeticsImpl(player.getUniqueId());
            userCosmeticsDbModel.addCosmetic(cosmetic);

            userCosmeticsManager.addUser(userCosmeticsDbModel);

            buyCosmetic(event, menuItem, player, userCoinsDbModel);
        }
    }

    private void buyCosmetic(InventoryClickEvent event, MenuItem menuItem, Player player, UserCoins userCoinsDbModel) {
        userCoinsDbModel.removeCoins(menuItem.getPrice());
        PlayerUtil.sendMessage(player, messages.getBoughtCosmetic());
        event.setCancelled(true);
    }

    private boolean hasCoins(InventoryClickEvent event, MenuItem menuItem, Player player, UserCoins userCoinsDbModel) {
        if (!userCoinsDbModel.hasCoins(menuItem.getPrice())) {
            PlayerUtil.sendMessage(player, messages.getDontHaveCoins());
            event.setCancelled(true);
            return true;
        }
        return false;
    }
}

package pl.timsixth.thetag.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.AbstractAction;
import pl.timsixth.gui.libray.model.action.ActionType;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.minigameapi.api.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmeticsDbModel;
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
        super("BUY_OR_ACTIVE_COSMETIC", ActionType.CLICK);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        UserCoinsManager<UserCoinsDbModel> userCoinsManager = theTagPlugin.getUserCoinsManager();
        UserCosmeticsManager<UserCosmeticsDbModel> userCosmeticsManager = theTagPlugin.getUserCosmeticsManager();

        Optional<UserCosmeticsDbModel> userCosmeticsOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());
        Optional<Cosmetic> cosmeticOptional = theTagPlugin.getCosmeticsManager().getCosmeticByName(getArgs().get(0));
        Optional<UserCoinsDbModel> userCoinsOptional = userCoinsManager.getUserByUuid(player.getUniqueId());

        if (!cosmeticOptional.isPresent()) {
            event.setCancelled(true);
            return;
        }

        if (!userCoinsOptional.isPresent()) {
            event.setCancelled(true);
            return;
        }

        Cosmetic cosmetic = cosmeticOptional.get();
        UserCoinsDbModel userCoinsDbModel = userCoinsOptional.get();

        if (userCosmeticsOptional.isPresent()) {
            UserCosmeticsDbModel userCosmeticsDbModel = userCosmeticsOptional.get();

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

            UserCosmeticsDbModel userCosmeticsDbModel = new UserCosmeticsImpl(player.getUniqueId());
            userCosmeticsDbModel.addCosmetic(cosmetic);

            userCosmeticsManager.addUser(userCosmeticsDbModel);

            buyCosmetic(event, menuItem, player, userCoinsDbModel);
        }
    }

    private void buyCosmetic(InventoryClickEvent event, MenuItem menuItem, Player player, UserCoinsDbModel userCoinsDbModel) {
        userCoinsDbModel.removeCoins(menuItem.getPrice());
        PlayerUtil.sendMessage(player, messages.getBoughtCosmetic());
        event.setCancelled(true);
    }

    private boolean hasCoins(InventoryClickEvent event, MenuItem menuItem, Player player, UserCoinsDbModel userCoinsDbModel) {
        if (!userCoinsDbModel.hasCoins(menuItem.getPrice())) {
            PlayerUtil.sendMessage(player, messages.getDontHaveCoins());
            event.setCancelled(true);
            return true;
        }
        return false;
    }
}

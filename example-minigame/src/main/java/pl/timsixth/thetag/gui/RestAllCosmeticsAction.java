package pl.timsixth.thetag.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.AbstractAction;
import pl.timsixth.gui.libray.model.action.ActionType;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmeticsDbModel;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;

public class RestAllCosmeticsAction extends AbstractAction implements ClickAction {

    private final TheTagPlugin theTagPlugin = TheTagPlugin.getPlugin(TheTagPlugin.class);

    public RestAllCosmeticsAction() {
        super("RESET_ALL_COSMETICS", ActionType.CLICK);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        UserCosmeticsManager<UserCosmeticsDbModel> userCosmeticsManager = theTagPlugin.getUserCosmeticsManager();

        Optional<UserCosmeticsDbModel> userCosmeticsOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());

        if (!userCosmeticsOptional.isPresent()) {
            event.setCancelled(true);
            return;
        }

        UserCosmeticsDbModel userCosmeticsDbModel = userCosmeticsOptional.get();

        userCosmeticsDbModel.resetAllCosmetics();

        PlayerUtil.sendMessage(player, theTagPlugin.getMessages().getResetAllCosmetics());
        event.setCancelled(true);
    }
}

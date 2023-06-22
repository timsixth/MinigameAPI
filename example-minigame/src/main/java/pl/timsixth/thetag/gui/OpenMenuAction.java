package pl.timsixth.thetag.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.AbstractAction;
import pl.timsixth.gui.libray.model.action.ActionType;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.minigameapi.util.ChatUtil;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;

public class OpenMenuAction extends AbstractAction implements ClickAction {

    private final TheTagPlugin theTagPlugin = TheTagPlugin.getPlugin(TheTagPlugin.class);

    public OpenMenuAction() {
        super("OPEN_MENU", ActionType.CLICK);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        Optional<Menu> menuOptional = theTagPlugin.getMenuManager().getMenuByName(getArgs().get(0));
        if (!menuOptional.isPresent()) {
            PlayerUtil.sendMessage(player, ChatUtil.chatColor("&cMenu does not exists!"));
            event.setCancelled(true);
            return;
        }

        player.openInventory(theTagPlugin.getMenuManager().createMenu(menuOptional.get()));

        event.setCancelled(true);
    }
}

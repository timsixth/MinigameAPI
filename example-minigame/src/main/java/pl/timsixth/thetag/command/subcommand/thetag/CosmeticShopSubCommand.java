package pl.timsixth.thetag.command.subcommand.thetag;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.minigameapi.command.SubCommand;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;
@RequiredArgsConstructor
public class CosmeticShopSubCommand implements SubCommand {

    private final MenuManager menuManager;
    private final Messages messages;
    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Optional<Menu> optionalMenu = menuManager.getMenuByName("main_cosmetics");

        if (!optionalMenu.isPresent()) {
            PlayerUtil.sendMessage(player, messages.getMenuDoesntExists());
            return true;
        }

        player.openInventory(menuManager.createMenu(optionalMenu.get()));

        return false;
    }

    @Override
    public String getName() {
        return "cosmeticshop";
    }
}
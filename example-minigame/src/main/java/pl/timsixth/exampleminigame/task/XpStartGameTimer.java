package pl.timsixth.exampleminigame.task;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.gamestate.PlayingGameState;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.timers.XpGameTimer;

public class XpStartGameTimer extends XpGameTimer {

    private final Messages messages;

    public XpStartGameTimer(Game game, Settings settings, Messages messages) {
        super(game, settings.getMinPlayers(), settings.getStartTimer());
        this.messages = messages;
    }

    private ItemStack getShovel() {
        ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("Magic shovel");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);

        item.setItemMeta(itemMeta);

        return item;
    }

    @Override
    public void onCancel() {
        game.sendMessage(messages.getStartingCanceled());
    }

    @Override
    public void onEnd() {
        super.onEnd();
        game.getPlayingUsers().forEach(userGame -> {
            Player player = userGame.toPlayer();

            player.getInventory().clear();
            player.getInventory().addItem(getShovel());

            player.teleport(game.getArena().getLocationByName("spawn"));
        });

        game.setState(new PlayingGameState());
    }
}

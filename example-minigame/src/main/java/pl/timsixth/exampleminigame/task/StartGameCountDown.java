package pl.timsixth.exampleminigame.task;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.gamestate.PlayingGameState;
import pl.timsixth.minigameapi.api.game.Game;

@AllArgsConstructor
public class StartGameCountDown extends BukkitRunnable {

    private int gameTimer;
    private final Game game;
    private final Messages messages;
    private final Settings settings;

    @Override
    public void run() {
        if (gameTimer > 0) {
            game.sendMessage(messages.getGameStarting() + gameTimer);
            gameTimer--;
            if (gameTimer == 0) {

                game.getPlayingUsers().forEach(userGame -> {
                    Player player = userGame.toPlayer();

                    player.getInventory().clear();
                    player.getInventory().addItem(getShovel());

                    player.teleport(game.getArena().getLocationByName("spawn"));
                });

                game.setState(new PlayingGameState());

                cancel();
                gameTimer = settings.getStartTimer();
            }
            if (game.getPlayingUsers().size() < settings.getMinPlayers()) {
                game.sendMessage(messages.getStartingCanceled());

                cancel();
                gameTimer = settings.getStartTimer();
            }
        }
    }

    private ItemStack getShovel() {
        ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("Magic shovel");
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);

        item.setItemMeta(itemMeta);

        return item;
    }
}

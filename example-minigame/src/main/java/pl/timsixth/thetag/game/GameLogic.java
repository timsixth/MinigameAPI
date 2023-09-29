package pl.timsixth.thetag.game;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.model.UserStats;
import pl.timsixth.minigameapi.api.stats.model.UserStatsImpl;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.cosmetics.CosmeticCategory;
import pl.timsixth.thetag.cosmetics.hit.HitParticleCosmetic;
import pl.timsixth.thetag.model.MyUserGame;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class GameLogic {

    private final GameManager gameManager;
    private final UserStatsManager statisticsManager;
    private final Messages messages;
    private final ArenaManager arenaManager;
    private final Settings settings;
    private final UserCosmeticsManager userCosmeticsManager;

    public MyUserGame randomUser(Game game) {
        return (MyUserGame) game.getPlayingUsers().get(ThreadLocalRandom.current().nextInt(game.getPlayingUsers().size()));
    }

    public void leaveFromGame(Player player) {
        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);
        if (!gameOptional.isPresent()) return;

        Game game = gameOptional.get();

        gameManager.leaveFromGame(game, player);
    }

    public void randomJoin(Player player) {
        String randomName = gameManager.randomGame();
        if (randomName == null || randomName.isEmpty()) {
            PlayerUtil.sendMessage(player, messages.getDontHaveAnyEmptyArena());
            return;
        }

        Optional<Arena> areaOptional = arenaManager.getArena(randomName);
        if (!areaOptional.isPresent()) return;
        Arena arena = areaOptional.get();
        gameManager.joinGame(arena, player);
    }

    public void checkTheTag(Game game) {
        for (UserGame playingUser : game.getPlayingUsers()) {
            MyUserGame myUserGame = (MyUserGame) playingUser;

            if (myUserGame.isTag()) {
                myUserGame.setPlaying(false);
                game.getPlayingUsers().remove(myUserGame);
                Player player = myUserGame.toPlayer();
                player.teleport(settings.getLobbyLocation());
                PlayerUtil.sendMessage(player, messages.getRemovedTheTagToPlayer());
                player.getInventory().clear();
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                Optional<UserStats> userStatsOptional = statisticsManager.getUser(player.getUniqueId(), game.getArena().getName());
                UserStats userStats;
                if (!userStatsOptional.isPresent()) {
                    userStats = new UserStatsImpl(player.getUniqueId(), player.getName(), game.getArena().getName());
                    statisticsManager.addNewUser(userStats);

                } else {
                    userStats = userStatsOptional.get();
                }
                userStats.addDefeat();
                showCosmetics(player, null, CosmeticCategory.DEFEAT.name());
                break;
            }
        }
    }

    public void showCosmetics(Player player, Player entity, String category) {
        Optional<UserCosmetics> userCosmeticsDbModelOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());

        if (!userCosmeticsDbModelOptional.isPresent()) return;

        UserCosmetics userCosmeticsDbModel = userCosmeticsDbModelOptional.get();

        for (Cosmetic cosmetic : userCosmeticsDbModel.getAllCosmeticsCategory(category)) {
            if (!userCosmeticsDbModel.isCosmeticEnable(cosmetic)) continue;

            if (cosmetic instanceof HitParticleCosmetic) {
                if (entity == null) continue;
                HitParticleCosmetic hitParticleCosmetic = (HitParticleCosmetic) cosmetic;

                hitParticleCosmetic.show(entity);
            } else {
                cosmetic.show(player);
            }
        }

    }
}

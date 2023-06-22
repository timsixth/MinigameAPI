package pl.timsixth.thetag.expansion;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManager;
import pl.timsixth.thetag.manager.StatisticsManager;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TheTagExpansion extends PlaceholderExpansion {

    private final StatisticsManager statisticsManager;
    private final UserCoinsManager<UserCoinsDbModel> userCoinsManager;

    @Override
    public @NotNull String getIdentifier() {
        return "thetag";
    }

    @Override
    public @NotNull String getAuthor() {
        return "timsixth";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("player_games_played")) {
            if (player == null) return null;

            return String.valueOf(statisticsManager.getTotalGamesPlayed(player.getUniqueId()));
        } else if (params.equalsIgnoreCase("player_wins")) {
            if (player == null) return null;

            return String.valueOf(statisticsManager.getTotalWins(player.getUniqueId()));
        } else if (params.equalsIgnoreCase("player_defeats")) {
            if (player == null) return null;

            return String.valueOf(statisticsManager.getTotalDefeats(player.getUniqueId()));
        } else if (params.equalsIgnoreCase("player_coins")) {
            UserCoinsDbModel userCoins = getUserCoins(player.getUniqueId());

            if (userCoins == null) return null;

            return String.valueOf(userCoins.getCoins());
        }
        return null;
    }

    private UserCoinsDbModel getUserCoins(UUID uuid) {
        Optional<UserCoinsDbModel> userCoinsDbModelOptional = userCoinsManager.getUserByUuid(uuid);

        return userCoinsDbModelOptional.orElse(null);
    }

}

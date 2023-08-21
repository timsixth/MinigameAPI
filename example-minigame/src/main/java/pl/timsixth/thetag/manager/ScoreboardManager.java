package pl.timsixth.thetag.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.util.ChatUtil;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.model.MyUserGame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ScoreboardManager {

    private final Settings settings;

    public void showScoreboard(Player player, Game game, int gameTime) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("game_scoreboard", "dummy", settings.getScoreboardTitle());

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Optional<UserGame> userGameOptional = game.getUserGame(player.getUniqueId());
        if (!userGameOptional.isPresent()) return;

        MyUserGame userGame = (MyUserGame) userGameOptional.get();

        List<String> lines = settings.getScoreboardLines();
        Map<Integer, String> lineWithId = new HashMap<>();
        for (String line : lines) {
            lineWithId.put(Integer.parseInt(line.split(";")[0]), ChatUtil.chatColor(line.split(";")[1]
                    .replace("{PLAYER_NAME}", player.getDisplayName())
                    .replace("{GAME_TIME}", String.valueOf(gameTime))
                    .replace("{IS_THE_TAG}", userGame.isTag() ? ChatUtil.chatColor("&aYes") : ChatUtil.chatColor("&cNo"))
            ));
        }
        lineWithId.forEach((id, value) -> {
            Score score = objective.getScore(value);
            score.setScore(id);
        });

        player.setScoreboard(scoreboard);
    }
}

package pl.timsixth.minigameapi.game.team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import pl.timsixth.minigameapi.game.user.UserGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link Team}
 */
@Getter
@Setter
public class TeamImpl implements Team {

    private final String name;
    private String displayName;
    private ChatColor color;
    private List<UserGame> users;

    public TeamImpl(String name, String displayName, ChatColor color) {
        this.name = name;
        this.displayName = displayName;
        this.color = color;
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(UserGame userGame) {
        users.add(userGame);
    }

    @Override
    public void removeUser(UserGame userGame) {
        users.add(userGame);
    }
}

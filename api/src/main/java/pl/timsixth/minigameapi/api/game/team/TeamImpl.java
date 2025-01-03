package pl.timsixth.minigameapi.api.game.team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import pl.timsixth.minigameapi.api.game.user.UserGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of {@link Team}
 */
@Getter
@Setter
public class TeamImpl implements Team {

    private final String name;
    private String displayName;
    private String colorAsString;
    private List<UserGame> users;

    public TeamImpl(String name, String displayName, ChatColor color) {
        this(name, displayName, color.name());
    }

    public TeamImpl(String name, String displayName, String colorAsString) {
        this.name = name;
        this.displayName = displayName;
        this.colorAsString = colorAsString;
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(UserGame userGame) {
        users.add(userGame);
    }

    @Override
    public void removeUser(UserGame userGame) {
        users.remove(userGame);
    }

    @Override
    public void setColor(String color) {
        this.colorAsString = color;
    }

    @Override
    public ChatColor getColor() {
        boolean isChatColor = Arrays.stream(ChatColor.values()).anyMatch(chatColor -> chatColor.name().equals(colorAsString));

        if (!isChatColor) {
            throw new IllegalStateException("Color as string is not a chat color");
        }

        return ChatColor.valueOf(colorAsString);
    }

    @Override
    public void setColor(ChatColor color) {
        this.colorAsString = color.name();
    }

}

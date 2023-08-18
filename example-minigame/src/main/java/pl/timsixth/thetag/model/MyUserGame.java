package pl.timsixth.thetag.model;

import lombok.Getter;
import lombok.Setter;
import pl.timsixth.minigameapi.api.game.user.UserGameImpl;

import java.util.UUID;

@Getter
@Setter
public class MyUserGame extends UserGameImpl {

    private boolean isTag;

    public MyUserGame(UUID uuid) {
        super(uuid);
        this.isTag = false;
    }
}

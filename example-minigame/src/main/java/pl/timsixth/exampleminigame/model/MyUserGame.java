package pl.timsixth.exampleminigame.model;

import lombok.Getter;
import pl.timsixth.minigameapi.api.game.user.UserGameImpl;

import java.util.UUID;

@Getter
public class MyUserGame extends UserGameImpl {

    private int brokeBlocksAmount;

    public MyUserGame(UUID uuid) {
        super(uuid);
        this.brokeBlocksAmount = 0;
    }

    public void addBlock() {
        brokeBlocksAmount++;
    }
}

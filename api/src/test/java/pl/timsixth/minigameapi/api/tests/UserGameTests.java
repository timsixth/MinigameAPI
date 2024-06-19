package pl.timsixth.minigameapi.api.tests;

import org.junit.Test;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.game.user.UserGameImpl;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserGameTests {

    @Test
    public void shouldAddPointsToUser() {
        UserGame userGame = new UserGameImpl(UUID.randomUUID(), 0, false);

        userGame.addPoints(10);

        assertEquals(10, userGame.getPoints());
    }

    @Test
    public void shouldRemovePointsFromUser() {
        UserGame userGame = new UserGameImpl(UUID.randomUUID(), 0, false);

        userGame.addPoints(10);
        int points = userGame.removePoints(1);

        assertEquals(9, points);
    }
}

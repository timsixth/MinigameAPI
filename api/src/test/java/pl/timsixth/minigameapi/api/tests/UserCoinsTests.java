package pl.timsixth.minigameapi.api.tests;

import org.junit.Test;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.tests.user.UserCoinsStub;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;

public class UserCoinsTests {

    @Test
    public void shouldAddCoinsToUser() {
        UserCoins userCoinsDbModel = new UserCoinsStub(UUID.randomUUID(), 10);

        userCoinsDbModel.addCoins(2);

        assertEquals(12, (int) userCoinsDbModel.getCoins());
    }
}

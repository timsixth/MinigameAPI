package pl.timsixth.minigameapi;

import org.junit.Before;
import org.junit.Test;
import pl.timsixth.databasesapi.database.structure.DataType;
import pl.timsixth.minigameapi.api.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.database.DataBase;
import pl.timsixth.minigameapi.user.UserCoinsStub;

import java.sql.SQLException;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;

public class UserCoinsTests {

    @Before
    public void init() throws SQLException {
        DataBase dataBase = new DataBase();
        dataBase.openConnection();


        DataBase.getMySQL().getTableCreator()
                .createColumn("id", DataType.INT, 11, false)
                .primaryKey("id", true)
                .autoIncrement("id", true)
                .createColumn("uuid", DataType.VARCHAR, 36, false)
                .createColumn("coins", DataType.INT, 11, false)
                .defaultValue("coins", 0)
                .create("users_coins_test");
    }


    @Test
    public void shouldAddCoinsToUser() {
        UserCoinsDbModel userCoinsDbModel = new UserCoinsStub(UUID.randomUUID(), 10);

        userCoinsDbModel.addCoins(2);

        userCoinsDbModel.save();

        assertEquals(12, (int) userCoinsDbModel.getCoins());
    }
}

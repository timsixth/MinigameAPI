package pl.timsixth.minigameapi.api.tests;

import pl.timsixth.minigameapi.api.tests.database.DataBase;
import pl.timsixth.minigameapi.api.tests.user.UserCoinsStub;
import org.junit.Before;
import org.junit.Test;
//import pl.timsixth.databasesapi.database.structure.datatype.DataTypes;
import pl.timsixth.minigameapi.api.coins.UserCoins;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;

public class UserCoinsTests {

    @Before
    public void init() {
        DataBase dataBase = new DataBase();
//        dataBase.openConnection();
//
//
//        DataBase.getMySQL().getTableCreator()
//                .id()
//                .createColumn("uuid", DataTypes.VARCHAR, false)
//                .createColumn("coins", DataTypes.INT, false)
//                .defaultValue("coins", 0)
//                .createTable("users_coins_test");
    }


    @Test
    public void shouldAddCoinsToUser() {
        UserCoins userCoinsDbModel = new UserCoinsStub(UUID.randomUUID(), 10);

        userCoinsDbModel.addCoins(2);

        assertEquals(12, (int) userCoinsDbModel.getCoins());
    }
}

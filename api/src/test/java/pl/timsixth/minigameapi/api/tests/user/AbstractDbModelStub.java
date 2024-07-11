package pl.timsixth.minigameapi.api.tests.user;

import pl.timsixth.minigameapi.api.tests.database.DataBase;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;

import java.util.concurrent.ExecutionException;

public abstract class AbstractDbModelStub extends AbstractDbModel {

    @Override
    public void executeUpdate(String query) {
//        try {
//            //DataBase.getMySQL().getAsyncQuery().update(query);
//        } catch (ExecutionException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}

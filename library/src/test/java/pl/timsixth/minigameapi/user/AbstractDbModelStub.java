package pl.timsixth.minigameapi.user;

import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.database.DataBase;

import java.util.concurrent.ExecutionException;

public abstract class AbstractDbModelStub extends AbstractDbModel {

    @Override
    protected void executeUpdate(String query) {
        try {
            DataBase.getMySQL().getAsyncQuery().update(query);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

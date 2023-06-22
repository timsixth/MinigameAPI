package pl.timsixth.minigameapi.coins.manager;

import pl.timsixth.minigameapi.coins.UserCoins;
import pl.timsixth.minigameapi.user.UserManager;

import java.util.List;

public interface UserCoinsManager<T extends UserCoins> extends UserManager<T> {

    List<T> getUsers();

    void addUser(T type);

    void removeUser(T type);
}

package pl.timsixth.minigameapi.api.coins.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.loader.Loader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The implementation of {@link UserCoinsManager}
 *
 * Every manager which works on loaded data must have injected loader
 */
@RequiredArgsConstructor
public class UserCoinsManagerImpl implements UserCoinsManager {

    private final Loader<UserCoins> userCoinsLoader;

    @Override
    public List<UserCoins> getUsers() {
        return userCoinsLoader.getData();
    }

    @Override
    public void addUser(UserCoins type) {
        userCoinsLoader.addObject(type);
    }

    @Override
    public void removeUser(UserCoins type) {
        userCoinsLoader.removeObject(type);
    }

    @Override
    public Optional<UserCoins> getUserByUuid(UUID uuid) {
        return userCoinsLoader.getData().stream()
                .filter(userCoinsDbModel -> userCoinsDbModel.getUuid().equals(uuid))
                .findAny();
    }
}

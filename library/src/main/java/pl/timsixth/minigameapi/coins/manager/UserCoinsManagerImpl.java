package pl.timsixth.minigameapi.coins.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.loader.UserCoinsLoader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The implementation of {@link UserCoinsManager}
 * @see UserCoinsDbModel
 *
 * Every manager which works on loaded data must have injected loader
 */
@RequiredArgsConstructor
public class UserCoinsManagerImpl implements UserCoinsManager<UserCoinsDbModel> {

    private final UserCoinsLoader userCoinsLoader;

    @Override
    public List<UserCoinsDbModel> getUsers() {
        return userCoinsLoader.getData();
    }

    @Override
    public void addUser(UserCoinsDbModel type) {
        userCoinsLoader.addObject(type);
    }

    @Override
    public void removeUser(UserCoinsDbModel type) {
        userCoinsLoader.removeObject(type);
    }

    @Override
    public Optional<UserCoinsDbModel> getUserByUuid(UUID uuid) {
        return userCoinsLoader.getData().stream()
                .filter(userCoinsDbModel -> userCoinsDbModel.getUuid().equals(uuid))
                .findAny();
    }
}

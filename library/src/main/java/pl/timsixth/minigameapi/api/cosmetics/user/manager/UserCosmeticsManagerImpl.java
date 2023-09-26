package pl.timsixth.minigameapi.api.cosmetics.user.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.loader.Loader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Every manager which works on loaded data must have injected loader
 */
@RequiredArgsConstructor
public class UserCosmeticsManagerImpl implements UserCosmeticsManager {

    private final Loader<UserCosmetics> userCosmeticsLoader;

    @Override
    public Optional<UserCosmetics> getUserByUuid(UUID uuid) {
        return userCosmeticsLoader.getData().stream()
                .filter(userCosmeticsDbModel -> userCosmeticsDbModel.getUuid().equals(uuid))
                .findAny();
    }

    @Override
    public List<UserCosmetics> getUsers() {
        return userCosmeticsLoader.getData();
    }

    @Override
    public void addUser(UserCosmetics user) {
        userCosmeticsLoader.addObject(user);
    }

    @Override
    public void removeUser(UserCosmetics user) {
        userCosmeticsLoader.removeObject(user);
    }
}

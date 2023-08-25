package pl.timsixth.minigameapi.api.cosmetics.user.manager;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmeticsDbModel;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Every manager which works on loaded data must have injected loader
 */
@RequiredArgsConstructor
public class UserCosmeticsManagerImpl implements UserCosmeticsManager<UserCosmeticsDbModel> {

    private final UserCosmeticsLoader userCosmeticsLoader;

    @Override
    public Optional<UserCosmeticsDbModel> getUserByUuid(UUID uuid) {
        return userCosmeticsLoader.getData().stream()
                .filter(userCosmeticsDbModel -> userCosmeticsDbModel.getUuid().equals(uuid))
                .findAny();
    }

    @Override
    public List<UserCosmeticsDbModel> getUsers() {
        return userCosmeticsLoader.getData();
    }

    @Override
    public void addUser(UserCosmeticsDbModel user) {
        userCosmeticsLoader.addObject(user);
    }

    @Override
    public void removeUser(UserCosmeticsDbModel user) {
        userCosmeticsLoader.removeObject(user);
    }
}

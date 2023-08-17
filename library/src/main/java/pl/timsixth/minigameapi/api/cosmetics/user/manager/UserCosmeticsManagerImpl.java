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
    public List<UserCosmeticsDbModel> getUsersCosmetics() {
        return userCosmeticsLoader.getData();
    }

    @Override
    public void addUserCosmetics(UserCosmeticsDbModel type) {
        userCosmeticsLoader.addObject(type);
    }

    @Override
    public void removeUserCosmetics(UserCosmeticsDbModel type) {
        userCosmeticsLoader.removeObject(type);
    }

    @Override
    public Optional<UserCosmeticsDbModel> getUserByUuid(UUID uuid) {
        return userCosmeticsLoader.getData().stream()
                .filter(userCosmeticsDbModel -> userCosmeticsDbModel.getUuid().equals(uuid))
                .findAny();
    }
}

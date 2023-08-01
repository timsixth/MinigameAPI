package pl.timsixth.minigameapi.booster.user.manager;

import pl.timsixth.minigameapi.booster.user.UserBoosters;
import pl.timsixth.minigameapi.user.UserManager;

import java.util.List;

public interface UserBoostersManager<T extends UserBoosters> extends UserManager<T> {

    List<T> getUsersBoosters();

    void addUserBoosters(T userBoosters);

    void removeUserBoosters(T userBoosters);
}
